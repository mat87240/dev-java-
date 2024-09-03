package Global;

public class Static {
	private static int differentiate;
	private static String levelNameInstance;
	private String string;
	private static int value;
	private static int debugPower;
	private static float zoom = 1;
	private static int maxDelta;
	private static int screenWidth, screenHeight;
	private static boolean isDead;
	private static int SelectedBlock;
	private static float gravity;
	// map maker
	private static boolean doSave;
	private static boolean canPressButton = false;
	private static int modState = 0, Movhandstate = 0;
	private static boolean swipe, select, rotate, snape;
	private static boolean pause, paramettre;
	private static int buildmod = 1;
	private static int pagemap = 1, maxpagemap = 1;
	//menu map maker
	private static boolean create;
	private static double menuScroll = 0;
	private static int index = 1, indexmax = 1;
	//SkinMenu
	private static int skinmod = 1;
	private static int pageskin = 1, maxpageskin = 1, pagecolor = 1, maxpagecolor = 1;
	private static int colornum = 1;

	private Static() {
	}

	public static int getDifferentiate() {
		return differentiate;
	}

	public static void setDifferentiate(int differentiate) {
		Static.differentiate = differentiate;
	}

	public static String getLevelName() {
		return levelNameInstance;
	}

	public static void setLevelName(String levelName) {
		levelNameInstance = levelName;
		System.out.println(levelName);
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getInt() {
		return value;
	}

	public void setInt(int value) {
		this.value = value;
	}

	public static float getMaxDelta() {
		return maxDelta;
	}

	public static void setMaxDelta(int maxDelta) {
		Static.maxDelta = maxDelta;
	}

	public static int getDebugPower() {
		return debugPower;
	}

	public static void setDebugPower(int debugPower) {
		Static.debugPower = debugPower;
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static void setScreenWidth(int width) {
		screenWidth = width;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int height) {
		screenHeight = height;
	}

	public static boolean getDoSave() {
		return doSave;
	}

	public static void setDoSave(boolean doSave) {
		Static.doSave = doSave;
	}

	public static boolean getCanPressButton() {
		return canPressButton;
	}

	public static void setCanPressButton(boolean canPressButton) {
		Static.canPressButton = canPressButton;
	}

	public static int getTotalScroll() {
		return value;
	}

	public static double getMenuScroll() {
		return menuScroll;
	}

	public static void setMenuScroll(double menuScroll) {
		Static.menuScroll = menuScroll;
	}

	public static void setTotalScroll(int value) {
		Static.value = value;
	}

	public static float getZoom() {
		return zoom;
	}

	public static void setZoom(float value) {
		Static.zoom = value;
	}

	public static void setIndex(int index) {
		Static.index = index;
	}

	public static int getIndex() {
		return Static.index;
	}

	public static void setIndexMax(int indexmax) {
		Static.indexmax = indexmax;
	}

	public static int getIndexMax() {
		return Static.indexmax;
	}

	public static void setMovhandstate(int index) {
		Static.Movhandstate = index;
	}

	public static int getMovhandstate() {
		return Static.Movhandstate;
	}

	public static void setModState(int index) {
		Static.modState = index;
	}

	public static int getModState() {
		return Static.modState;
	}

	public static void setSwipe(boolean a) {
		Static.swipe = a;
	}

	public static boolean getSwipe() {
		return Static.swipe;
	}

	public static void setSelect(boolean a) {
		Static.select = a;
	}

	public static boolean getSelect() {
		return Static.select;
	}

	public static void setSnape(boolean a) {
		Static.snape = a;
	}

	public static boolean getSnape() {
		return Static.snape;
	}

	public static void setRotate(boolean a) {
		Static.rotate = a;
	}

	public static boolean getRotate() {
		return Static.rotate;
	}

	public static void setPagemapbuild(int index) {
		Static.pagemap = index;
	}

	public static int getPagemapbuild() {
		return Static.pagemap;
	}

	public static void setMaxpagemapbuild(int indexmax) {
		Static.maxpagemap = indexmax;
	}

	public static int getMaxpagemapbuild() {
		return Static.maxpagemap;
	}

	public static void setPause(boolean a) {
		Static.pause = a;
	}

	public static boolean getPause() {
		return Static.pause;
	}

	public static void setParamettre(boolean a) {
		Static.paramettre = a;
	}

	public static boolean getParamettre() {
		return Static.paramettre;
	}

	public static void setCreate(boolean a) {
		Static.create = a;
	}

	public static boolean getCreate() {
		return Static.create;
	}

	public static void setBuildmod(int a) {
		Static.buildmod = a;
	}

	public static int getBuildmod() {
		return Static.buildmod;
	}

	public static void setPageskin(int index) {
		Static.pageskin = index;
	}

	public static int getPageskin() {
		return Static.pageskin;
	}

	public static void setMaxpageskin(int indexmax) {
		Static.maxpageskin = indexmax;
	}

	public static int getMaxpageskin() {
		return Static.maxpageskin;
	}

	public static void setPagecolor(int index) {
		Static.pagecolor = index;
	}

	public static int getPagecolor() {
		return Static.pagecolor;
	}

	public static void setMaxpagecolor(int indexmax) {
		Static.maxpagecolor = indexmax;
	}

	public static int getMaxpagecolor() {
		return Static.maxpagecolor;
	}

	public static void setSkinmod(int a) {
		Static.skinmod = a;
	}

	public static int getSkinmod() {
		return Static.skinmod;
	}

	public float getGravity() {
		return gravity;
	}

	public static void setGravity(float gravity) {
		Static.gravity = gravity;
	}

	public static boolean getIsDead() {
		return isDead;
	}

	public static void setIsDead(boolean isDead) {
		Static.isDead = isDead;
	}

	public static int getSelectedBlock() {
		return SelectedBlock;
	}

	public static void setSelectedBlock(int selectedBlock) {
		SelectedBlock = selectedBlock;
	}

	public static int getColorNum() {
		return colornum;
	}
	public static void setColorNum(int valeur) {
		Static.colornum = valeur;
	}
}