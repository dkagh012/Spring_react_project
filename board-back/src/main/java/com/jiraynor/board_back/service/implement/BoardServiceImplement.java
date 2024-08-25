package com.jiraynor.board_back.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.jiraynor.board_back.DTO.request.board.PatchBoardRequestDto;
import com.jiraynor.board_back.DTO.request.board.PostBoardRequestDto;
import com.jiraynor.board_back.DTO.request.board.PostCommentRequestDto;
import com.jiraynor.board_back.DTO.response.ResponseDto;
import com.jiraynor.board_back.DTO.response.board.DeleteBoardResponseDto;
import com.jiraynor.board_back.DTO.response.board.GetBoardResponseDto;
import com.jiraynor.board_back.DTO.response.board.GetCommentListResponseDto;
import com.jiraynor.board_back.DTO.response.board.GetFavoriteListResponseDto;
import com.jiraynor.board_back.DTO.response.board.GetLatestBoardListResponseDto;
import com.jiraynor.board_back.DTO.response.board.GetSearchBoardListResponseDto;
import com.jiraynor.board_back.DTO.response.board.GetTop3BoardListResponseDto;
import com.jiraynor.board_back.DTO.response.board.IncreaseViewCountResponseDto;
import com.jiraynor.board_back.DTO.response.board.PatchBoardResponseDto;
import com.jiraynor.board_back.DTO.response.board.PostBoardResponseDto;
import com.jiraynor.board_back.DTO.response.board.PostCommentResponseDto;
import com.jiraynor.board_back.DTO.response.board.PutFavoriteResponseDto;
import com.jiraynor.board_back.entity.BoardEntity;
import com.jiraynor.board_back.entity.BoardListViewEntity;
import com.jiraynor.board_back.entity.CommentEntity;
import com.jiraynor.board_back.entity.FavoriteEntity;
import com.jiraynor.board_back.entity.ImageEntity;
import com.jiraynor.board_back.entity.SearchLogEntity;
import com.jiraynor.board_back.repository.BoardListViewRepository;
import com.jiraynor.board_back.repository.BoardRepository;
import com.jiraynor.board_back.repository.CommentRepository;
import com.jiraynor.board_back.repository.FavoriteRepository;
import com.jiraynor.board_back.repository.ImageRepository;
import com.jiraynor.board_back.repository.SearchLogRepository;
import com.jiraynor.board_back.repository.UserRepository;
import com.jiraynor.board_back.repository.resultSet.GetBoardResultSet;
import com.jiraynor.board_back.repository.resultSet.GetCommentListResultSet;
import com.jiraynor.board_back.repository.resultSet.GetFavoriteListResultSet;
import com.jiraynor.board_back.service.BoardService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

    // 의존성 주입을 통해 각 리포지토리에 접근할 수 있습니다.
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;
    private final SearchLogRepository searchLogRepository;
    private final BoardListViewRepository boardListViewRepository;

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        
        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities =new ArrayList<>();

        try{
             resultSet = boardRepository.getBoard(boardNumber);
            if(resultSet == null) return GetBoardResponseDto.noExistBoard();

            imageEntities = imageRepository.findByBoardNumber(boardNumber);

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetBoardResponseDto.success(resultSet,imageEntities);
    }

    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {
        
        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();

        try{
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetFavoriteListResponseDto.noExistBoard();

            resultSets = favoriteRepository.getFavoriteList(boardNumber);

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetFavoriteListResponseDto.success(resultSets);

    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {
        
        List<GetCommentListResultSet> resultSets = new ArrayList<>();
        
        try{

            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetCommentListResponseDto.noExistBoard();
            
            resultSets = commentRepository.getCommentList(boardNumber);

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetCommentListResponseDto.success(resultSets);
    }


    @Override
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {
        
        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try{

            boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc();

        } catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetLatestBoardListResponseDto.success(boardListViewEntities);

    }


    @Override
    public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {
        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();

        try{
            Date beforeWeek = Date.from(Instant.now().minus(7,ChronoUnit.DAYS));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sevenDaysAgo = simpleDateFormat.format(beforeWeek);

            boardListViewEntities = boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetTop3BoardListResponseDto.success(boardListViewEntities);
    
    }
    
    
    @Override
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord,
            String preSearchWord) {
                List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
                try{

                    boardListViewEntities = boardListViewRepository.findByTitleContainsOrContentContainsOrderByWriteDatetimeDesc(searchWord, preSearchWord);

                    SearchLogEntity searchLogEntity = new SearchLogEntity(searchWord,preSearchWord,false);
                    searchLogRepository.save(searchLogEntity);

                    boolean relation = preSearchWord != null;

                    if(relation) {
                        searchLogEntity = new SearchLogEntity(preSearchWord,searchWord,relation);
                        searchLogRepository.save(searchLogEntity);
                    }
                    
                }catch(Exception exception){
                    exception.printStackTrace();
                    return ResponseDto.databaseError();
                }
                return GetSearchBoardListResponseDto.success(boardListViewEntities);
    }


    
    // 게시물을 생성하고 저장하는 메서드입니다.
    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {
        try {
            // 사용자의 이메일이 존재하는지 확인합니다.
            boolean existedEmail = userRepository.existsByEmail(email);
            if (!existedEmail) return PostBoardResponseDto.notExistUser();

            // 게시물 엔티티를 생성하고 저장합니다.
            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRepository.save(boardEntity);

            // 생성된 게시물의 고유 번호를 가져옵니다.
            int boardNumber = boardEntity.getBoardNumber();

            // 게시물에 첨부된 이미지 리스트를 가져와서 이미지 엔티티 리스트를 생성합니다.
            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for (String image : boardImageList) {
                // 각 이미지에 대해 이미지 엔티티를 생성하고 리스트에 추가합니다.
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }

            // 이미지 엔티티들을 데이터베이스에 저장합니다.
            imageRepository.saveAll(imageEntities);

        } catch (Exception exception) {
            // 예외가 발생하면 스택 트레이스를 출력하고 데이터베이스 오류 응답을 반환합니다.
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        // 성공적인 게시물 생성 후 성공 응답을 반환합니다.
        return PostBoardResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto,Integer boardNumber, String email) {
        try{

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return PostCommentResponseDto.noExistBoard();

            boolean existedUser = userRepository.existsByEmail(email); 
            if (!existedUser) return PostCommentResponseDto.noExistUser(); 

            CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
            commentRepository.save(commentEntity);

            boardEntity.increaseCommentCount();
            boardRepository.save(boardEntity);


        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PostCommentResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {

        try{
            boolean existedUser = userRepository.existsByEmail(email);
            if(!existedUser) return PutFavoriteResponseDto.noExistUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return PutFavoriteResponseDto.noExistBoard();

            FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
            if(favoriteEntity == null){
                favoriteEntity = new FavoriteEntity(email,boardNumber);
                favoriteRepository.save(favoriteEntity);
                boardEntity.increaseFavoriteCount();
            }else{
                favoriteRepository.delete(favoriteEntity);
                boardEntity.decreaseFavoriteCount();
            }
            boardRepository.save(boardEntity);
            

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return PutFavoriteResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber,
            String email) {

                try{

                    BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
                    if(boardEntity == null) return PatchBoardResponseDto.noExistBoard();

                    boolean existedUser = userRepository.existsByEmail(email);
                    if(!existedUser) return PatchBoardResponseDto.noExistUser();

                    String writerEmail = boardEntity.getWriterEmail();
                    boolean isWriter = writerEmail.equals(email);
                    if(!isWriter) return PatchBoardResponseDto.noPermission();

                    boardEntity.patchBoard(dto);
                    boardRepository.save(boardEntity);

                    imageRepository.deleteByBoardNumber(boardNumber);
                    List<String> boardImageList = dto.getBoardImageList();
                    List<ImageEntity> imageEntities = new ArrayList();

                    for (String image: boardImageList){
                        ImageEntity imageEntity = new ImageEntity(boardNumber , image);
                        imageEntities.add(imageEntity);
                    }

                    imageRepository.saveAll(imageEntities);

                }catch(Exception exception){
                    exception.printStackTrace();
                    return ResponseDto.databaseError();
                }
                return PatchBoardResponseDto.success();
    }


    @Override
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {
    
        try{

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return IncreaseViewCountResponseDto.noExistBoard();

            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);
        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return IncreaseViewCountResponseDto.success();
    }

    @Override
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {
            try{

                boolean existedUser = userRepository.existsByEmail(email);
                if (!existedUser) return DeleteBoardResponseDto.noExistUser();
                
                BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
                if (boardEntity == null) return DeleteBoardResponseDto.noExistBoard();
                
                String writerEmail = boardEntity.getWriterEmail();
                boolean isWriter = writerEmail.equals(email);
                if (!isWriter) return DeleteBoardResponseDto.noPermission();

                imageRepository.deleteByBoardNumber(boardNumber);
                commentRepository.deleteByBoardNumber(boardNumber);
                favoriteRepository.deleteByBoardNumber(boardNumber);

                boardRepository.delete(boardEntity);

            }catch(Exception exception){
                exception.printStackTrace();
                return ResponseDto.databaseError();
            }
            return DeleteBoardResponseDto.success();
    }





}
