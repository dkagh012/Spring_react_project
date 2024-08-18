import { useEffect, useState } from "react";

const usePagination = <T>(countPerPage: number) => {
  // state: 전체 객체 리스트 상태를 관리하기 위한 state
  const [totalList, setTotalList] = useState<T[]>([]);

  // state: 현재 페이지에 보여줄 객체 리스트 상태를 관리하기 위한 state
  const [viewList, setViewList] = useState<T[]>([]);

  // state: 현재 페이지 번호를 관리하기 위한 state
  const [currentPage, setCurrentPage] = useState<number>(1);

  // state: 전체 페이지 번호 리스트 상태를 관리하기 위한 state
  const [totalPageList, setTotalPageList] = useState<number[]>([1]);

  // state: 현재 섹션에 보여줄 페이지 번호 리스트 상태를 관리하기 위한 state
  const [viewPageList, setViewPageList] = useState<number[]>([1]);

  // state: 현재 섹션 번호를 관리하기 위한 state
  const [currentSection, setCurrentSection] = useState<number>(1);

  // state: 전체 섹션 수를 관리하기 위한 state
  const [totalSection, setTotalSection] = useState<number>(1);

  // 보여줄 객체 리스트 추출 함수
  const setView = () => {
    // 첫 번째 인덱스를 계산
    const FIRST_INDEX = countPerPage * (currentPage - 1);
    // 마지막 인덱스를 계산 (리스트의 길이를 넘지 않도록)
    const LAST_INDEX =
      totalList.length > countPerPage * currentPage
        ? countPerPage * currentPage
        : totalList.length;
    // 전체 리스트에서 해당 인덱스 범위에 해당하는 부분을 잘라서 viewList에 저장
    const viewList = totalList.slice(FIRST_INDEX, LAST_INDEX);
    setViewList(viewList);
  };

  // 보여줄 페이지 리스트 추출 함수
  const setViewPage = () => {
    // 첫 번째 페이지 인덱스를 계산
    const FIRST_INDEX = 10 * (currentSection - 1);
    // 마지막 페이지 인덱스를 계산 (페이지 리스트의 길이를 넘지 않도록)
    const LAST_INDEX =
      totalPageList.length > 10 * currentSection
        ? 10 * currentSection
        : totalPageList.length;
    // 전체 페이지 리스트에서 해당 인덱스 범위에 해당하는 부분을 잘라서 viewPageList에 저장
    const viewPageList = totalPageList.slice(FIRST_INDEX, LAST_INDEX);
    setViewPageList(viewPageList);
  };

  // 전체 리스트가 변경될 때마다 실행되는 useEffect
  useEffect(() => {
    // 전체 페이지 수를 계산
    const totalPage = Math.ceil(totalList.length / countPerPage);
    // 페이지 리스트 초기화
    const totalPageList: number[] = [];
    // 페이지 리스트에 페이지 번호 추가
    for (let page = 1; page <= totalPage; page++) totalPageList.push(page);
    // 전체 페이지 리스트를 상태로 설정
    setTotalPageList(totalPageList);

    // 전체 섹션 수를 계산하여 상태로 설정
    const totalSection = Math.ceil(totalList.length / (countPerPage * 10));
    setTotalSection(totalSection);

    // 현재 페이지와 섹션을 초기값(1)으로 설정
    setCurrentPage(1);
    setCurrentSection(1);

    // 객체 리스트와 페이지 리스트를 업데이트
    setView();
    setViewPage();
  }, [totalList]);

  // 현재 페이지가 변경될 때마다 보여줄 객체 리스트를 업데이트하는 useEffect
  useEffect(setView, [currentPage]);

  // 현재 섹션이 변경될 때마다 보여줄 페이지 리스트를 업데이트하는 useEffect
  useEffect(setViewPage, [currentPage]);

  // 이 훅에서 반환할 값들
  return {
    currentPage, // 현재 페이지 번호
    setCurrentPage, // 현재 페이지 번호를 설정하는 함수
    currentSection, // 현재 섹션 번호
    setCurrentSection, // 현재 섹션 번호를 설정하는 함수
    viewList, // 현재 페이지에 보여줄 객체 리스트
    viewPageList, // 현재 섹션에 보여줄 페이지 리스트
    totalSection, // 전체 섹션 수
    setTotalList, // 전체 객체 리스트를 설정하는 함수
  };
};

export default usePagination;
