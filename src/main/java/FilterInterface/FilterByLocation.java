package FilterInterface;

import java.io.Serializable;
import java.util.LinkedList;

import parameters.Wifi;



/**The parameters idea of the filter is to get parameters from the user s.t the lowest geo' point and the highest geo' point ,
 * and filter the whole data base by that input , finish the filtering with <wifi> collection that holds user request.
 * after that filter the collection can be export to other task , s.t export to file or be filtered again , by new values.
 * 
 * the collection 'cloneList' is made to clone the input , to save the data base as it.
 * the collection resultCollection is the final collection that user get at the end .
 * @author recon
 *
 */
public class FilterByLocation implements Filter,Serializable{
	private double startlat, startlon , startalt;
	private double endlat, endlon, endalt;
	private LinkedList<Wifi> resultCollection;
	private LinkedList<Wifi> cloneList;
	private boolean checkIt;
	
	// +---------------------------- / Constructor / ------------------------------------------
	public FilterByLocation(
			double startlat,
			double startlon,
			double startalt,
			double endlat,
			double endlon, 
			double endalt,
			LinkedList<Wifi> insertlist , boolean flag ,
			LinkedList<Wifi> otherAlreadyFiltered)
	{
		this.startalt = startalt;
		this.startlat = startlat;
		this.startlon = startlon;
		this.endalt = endalt;
		this.endlat = endlat;
		this.endlon = endlon;
		this.checkIt = flag;
		resultCollection = new LinkedList<Wifi>();
		cloneList = new LinkedList<>();
		for(Wifi a : insertlist) {
			cloneList.add(a);
		}

		runFilter();
		if(otherAlreadyFiltered != null) { // filter again by the given collection
			runSecondStageFilter(otherAlreadyFiltered);
		}
	}
	
	private void runSecondStageFilter(LinkedList<Wifi> otherAlreadyFiltered) {
		if(checkIt) {
			for(Wifi a: otherAlreadyFiltered) 
				if(passes(a) && !resultCollection.contains(a))this.resultCollection.add(a);
		}else {
			for(Wifi a : otherAlreadyFiltered)
				if(!passes(a) && !resultCollection.contains(a))this.resultCollection.add(a);
		}
	}

	@Override
	public boolean passes(Wifi a) {
		if( ((Wifi)a).getLAT_double()<= this.endlat && ((Wifi)a).getLAT_double() >= this.startlat &&
				((Wifi)a).getLON_double() <= this.endlon && ((Wifi)a).getLON_double() >= this.startlon &&
				((Wifi)a).getALT_double() <= this.endalt && ((Wifi)a).getALT_double()>= this.startalt)return true;
		return false;
	}
	
	private void runFilter() {
		if(checkIt) { //checkIt == true , we want to collect that wifi
			for(Wifi a : cloneList) {
				if(passes(a)) {
					setWifi(a);
				}
			}
		}else { // checkIt == false ," NOT(wifi) "
			for(Wifi a : cloneList) {
				if(!passes(a)) {
					setWifi(a);
				}
			}
		}
	}
	
	public LinkedList<Wifi> get_resultList() {
		return this.resultCollection;
	}
	
	public void setWifi(Wifi a) {
		this.resultCollection.add(a);
	}

	
	

}
