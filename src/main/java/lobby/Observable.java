package lobby;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observers;

    public Observable() {
        observers = new ArrayList<Observer>();
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        for(Observer observer : this.observers)
            observer.update();
    }
    public <C> void notifyObservers(C change) {
        for(Observer observer : this.observers)
            observer.update(change);
    }
}
