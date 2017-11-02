package lobby;

public interface Observer<C> {
    void update(C change);
}
