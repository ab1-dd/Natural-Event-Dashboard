package interface_adapter.addToFavourite;

public class AddToFavouriteViewModel{
    private String message;

    public AddToFavouriteViewModel(){
        this.message = "";
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String newMessage){
        this.message = newMessage;
    }
}
