package connector.sqlite.doctor.dao.data;


public class DoctorWorkTableList {

	private String[] workTableList = {
		"createRD005_EXPORT"
		,"createRD006_O_ITAB"
		,"createRD006_O_ITAB2" 
		,"createRD006_O_ITAB3"
		,"createRD006_O_ITAB4"
		,"createRD007_O_ITAB"
		,"createRD008_O_ITAB1"
		,"createRD008_O_ITAB2"
		,"createRD017_O_ITAB"
		,"createRD018_O_ITAB"
		,"createRD018_O_ITAB2"
		,"createRD026_O_ITAB"
		,"createRD027_O_ITAB"
		,"createRD036_O_ITAB1"
		,"createRD036_O_ITAB2"
		,"createRD042_O_ITAB1"
		,"createRD042_O_ITAB2"
		,"createRD042_O_ITAB3"
		,"createRD042_O_ITAB4"
		,"createWR013_IMPORT"
		,"createWR013_I_ITAB1"
		,"createWR013_I_ITAB2"
		,"createWR013_I_ITAB5"
		,"createWR014_IMPORT"
		,"createWR014_I_ITAB1"
		,"createWR014_I_ITAB3"
		,"createWR014_I_ITAB5" 
		,"createWR015_IMPORT"
		,"createWR015_I_ITAB1"
		,"createWR015_I_ITAB2"
		,"createWR016_IMPORT"
		,"createWR016_I_ITAB" 
		,"createWR021_IMPORT"
		,"createWR021_I_ITAB"
		,"createWR060_IMPORT" 
		,"createWR060_I_ITAB" 
		,"createWR063_IMPORT"
		,"createWR061_IMPORT" 
		,"createWR065_IMPORT" 
		,"createWR067_IMPORT" 
		,"createWRNAN_I_ITAB"
		,"createWRVOC_I_ITAB"
	};
	

	public String[] getWorkTableList() {
		
		return workTableList;
	}
	
	
	public String getWorkTableName(int index) {
		
		return workTableList[index];
	}
	
	public int getWorkTableCount() {
		
		return workTableList.length;
	}
	
}
