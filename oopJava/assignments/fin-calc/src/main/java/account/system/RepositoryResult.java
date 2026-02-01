package account.system;

public sealed interface RepositoryResult<T> permits RepositoryResult.Failure, RepositoryResult.Success {
    T value();

    boolean isStateModified();

    String description();

    boolean isSuccess();

    record Success<T>(T value, boolean isStateModified, String description) implements RepositoryResult<T> {
        @Override
        public boolean isSuccess() {
            return true;
        }
    }
    record Failure<T>(T value, boolean isStateModified, String description) implements RepositoryResult<T> {
        @Override
        public boolean isSuccess() {
            return false;
        }
    }
}