package kakao.login;

public class BaseResponse<T> {
    private boolean success;
    private T data;

    public BaseResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public static <T> BaseResponse<T> onSuccess(T data) {
        return new BaseResponse<>(true, data);
    }
}
