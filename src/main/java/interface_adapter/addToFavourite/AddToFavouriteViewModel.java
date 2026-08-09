package interface_adapter.addToFavourite;

public class AddToFavouriteViewModel{
    private final String message;

    public AddToFavouriteViewModel(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
