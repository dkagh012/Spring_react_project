import {
  ChangeEvent,
  Dispatch,
  KeyboardEvent,
  forwardRef,
  SetStateAction,
} from "react";
import "./style.css";

// Props 인터페이스 정의, InputBox 컴포넌트가 받을 props의 타입을 명시
interface Props {
  label: string; // 입력 필드의 라벨 텍스트
  type: "text" | "password"; // 입력 필드의 타입 (텍스트 또는 비밀번호)
  placeholder: string; // 입력 필드의 플레이스홀더 텍스트
  value: string; // 입력 필드의 값
  setValue: Dispatch<SetStateAction<string>>; // 입력 값을 업데이트하는 함수
  error: boolean; // 에러 상태를 나타내는 boolean 값
  icon?: string; // 입력 필드의 아이콘 (선택 사항)
  onButtonClick?: () => void; // 버튼 클릭 이벤트 핸들러 (선택 사항)
  message?: string; // 입력 필드 아래에 표시할 메시지 (선택 사항)
  onKeyDown?: (event: KeyboardEvent<HTMLInputElement>) => void; // 키다운 이벤트 핸들러 (선택 사항)
}

// forwardRef를 사용하여 입력 요소에 ref를 전달하는 InputBox 컴포넌트 정의
const InputBox = forwardRef<HTMLInputElement, Props>((props: Props, ref) => {
  // props를 구조 분해 할당하여 쉽게 접근
  const { label, type, placeholder, value, error, icon, message } = props;
  const { setValue, onButtonClick, onKeyDown } = props;

  // 입력 필드의 변경 이벤트를 처리하는 함수
  const onChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setValue(value); // 전달된 setValue 함수를 사용하여 값을 업데이트
  };

  // 키다운 이벤트를 처리하는 함수
  const onKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
    if (!onKeyDown) return;
    onKeyDown(event); // onKeyDown prop이 존재하면 호출
  };

  return (
    <div className="inputbox">
      {/* 입력 필드의 라벨 */}
      <div className="inputbox-label">{label}</div>

      {/* 입력 필드와 아이콘을 감싸는 컨테이너, 에러 상태에 따른 스타일링 */}
      <div
        className={error ? "inputbox-container-error" : "inputbox-container"}
      >
        <input
          ref={ref} // 전달된 ref
          type={type} // 입력 필드의 타입 (텍스트 또는 비밀번호)
          className="input"
          placeholder={placeholder} // 플레이스홀더 텍스트
          value={value} // 입력 값
          onChange={onChangeHandler} // 변경 이벤트 핸들러
          onKeyDown={onKeyDownHandler} // 키다운 이벤트 핸들러
        />
        {/* 아이콘 버튼 (선택 사항) */}
        {onButtonClick !== undefined && (
          <div className="icon-button">
            {icon !== undefined && <div className={`icon${icon}`}></div>}
          </div>
        )}
      </div>

      {/* 입력 필드 아래에 표시할 메시지 (선택 사항) */}
      {message !== undefined && (
        <div className="inputbox-message">{message}</div>
      )}
    </div>
  );
});

export default InputBox;
