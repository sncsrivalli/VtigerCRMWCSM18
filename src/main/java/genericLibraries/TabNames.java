package genericLibraries;

public enum TabNames {
	
	ORGANIZATIONS("Organizations"),SIGNOUT("Sign Out");

	private String tabName;
	private TabNames(String tab) {
		this.tabName = tab;
	}
	
	public String getTabName() {
		return tabName;
	}

}
