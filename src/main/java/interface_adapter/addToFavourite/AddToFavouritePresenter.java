package interface_adapter.addToFavourite;

import use_case.addToFavourite.AddToFavouriteOutputBoundary;
import javax.swing.JOptionPane;

public class AddToFavouritePresenter implements AddToFavouriteOutputBoundary {

    @Override
    public void prepareSuccessView(String eventTitle) {
        JOptionPane.showMessageDialog(null,
                "Successfully added 【" + eventTitle + "】 to favourites!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void prepareFailView(String eventTitle, String errorMessage) {
        JOptionPane.showMessageDialog(null,
                errorMessage,
                "Duplicate Event",
                JOptionPane.WARNING_MESSAGE);
    }
}