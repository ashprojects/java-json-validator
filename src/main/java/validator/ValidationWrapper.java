package validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Developed by Ashish Gupta on 9/8/2019 9:57 PM
 */
public class ValidationWrapper {
    private boolean hasPassed;
    private List<String> messages;

    public ValidationWrapper(){
        this.hasPassed = true;
        this.messages = new ArrayList<>();
    }

    public ValidationWrapper(boolean hasPassed, List<String> messages) {
        this.hasPassed = hasPassed;
        this.messages = messages;
    }

    public ValidationWrapper addMessage(String message){
        this.messages.add(message);
        return this;
    }


    public ValidationWrapper mergeIn(ValidationWrapper other){
        this.messages.addAll(other.messages);
        this.hasPassed = this.hasPassed && other.hasPassed;
        return this;
    }


    public ValidationWrapper setPassed(boolean status){
        this.hasPassed = status;
        this.messages.clear();
        return this;
    }

    public boolean passed(){
        return hasPassed;
    }

    public boolean failed(){
        return !hasPassed;
    }

    @Override
    public String toString() {
        return "ValidationWrapper{" + "hasPassed=" + hasPassed + ", messages=" + messages + '}';
    }

}
