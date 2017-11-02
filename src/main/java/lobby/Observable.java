package lobby;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<C> {

    private List<Observer<C>> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public void registerObserver(Observer<C> observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer<C> observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers(C change) {
        for(Observer<C> observer : this.observers)
            observer.update(change);
    }
}
