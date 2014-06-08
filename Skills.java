
public class Skills {
	int skillH;
	int skillE;
	int skillP;
	Skills(int skillH,int skillE,int skillP)
	{
		this.skillE=skillE;
		this.skillH=skillH;
		this.skillP=skillP;
	}
	public int getSkillH() {
		return skillH;
	}
	public void setSkillH(int skillH) {
		this.skillH = skillH;
	}
	public int getSkillE() {
		return skillE;
	}
	public void setSkillE(int skillE) {
		this.skillE = skillE;
	}
	public int getSkillP() {
		return skillP;
	}
	public void setSkillP(int skillP) {
		this.skillP = skillP;
	}
	
	//Function to calculate Dot Product
	public int dotProduct(Skills x)
	{
		 return ((skillH*x.getSkillH())+(skillE*x.getSkillE())+(skillP*x.getSkillP()));
	}
}
