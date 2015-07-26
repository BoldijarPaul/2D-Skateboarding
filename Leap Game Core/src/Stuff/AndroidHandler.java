package Stuff;

public interface AndroidHandler {
	public void ShowAds(boolean show);

	public void LoadInterestial();

	public void ShowInterestial();

	public void ShowInfoDialog(String text);

	public void ShowInfoDialog(String text, String title);

	public void ShowBuyConfirmation(String text, int nr);

	public void ShowFBDialog();

	public void ShowRateDialog();

	public void ShowBuyDialog();

	public void ShowExitDialog();
}
