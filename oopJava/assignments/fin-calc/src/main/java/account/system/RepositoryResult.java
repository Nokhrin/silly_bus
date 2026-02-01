package account.system;

public record RepositoryResult<T>(
        T value,
        boolean isStaisStateModified
) {

    public static <T> RepositoryResult<T> success(T value, boolean modified) {
        return new RepositoryResult<>(value, modified);
    }

    public static <T> RepositoryResult<T> failure(T value, boolean modified) {
        return new RepositoryResult<>(value, modified);
    }
}
