package FilterInterface;

import java.io.Serializable;
import java.util.LinkedList;

/*
*The parameters Filiter that executes other filters by the given options.
*	The user input the setting for the filtering in GUI interface s.t he chooses the right filter (By-Time , By-Location, 
*	By-Device) after he choosed the filter , The data can be filtered only by the setted filter or by another filter.
*	i.e Filter: By-Device AND By-Time  , Filter: By-Device OR By-Time etc.
*	at the end The user got his data filterd. and saved to CSV file moreover the settings that was created the filter 
*	is saved too , to the *.ser file
	author : @recon
*/
import IO.CreateDB;
import parameters.Wifi;	

public class allFilters implements Serializable{
	public  String userStartTime , userEndTime ;
	public   String userDevice;
	public  double userStarLat , userEndLat , userStarLon , userEndLon, userStartAlt , userEndAlt ;
	public transient static LinkedList<Wifi> DBi = new LinkedList<Wifi>();
	boolean save;
	public boolean _time,_device,_location,_and,_or;
	//	private boolean timeFilter;
	//	private boolean deviceFilter;
	//	private boolean locationFilter;

	public transient LinkedList<Wifi> resultColl;

	public allFilters(boolean save,boolean time , boolean device , boolean location , boolean and , boolean or,
			String userStartTime ,String userEndTime ,
			String userDevice,
			String userStarLat ,String userEndLat ,String userStarLon ,String userEndLon,String userStartAlt ,String userEndAlt ) {
		this.userStarLat = Double.parseDouble(userStarLat);
		this.userEndLat = Double.parseDouble(userEndLat);
		this.userStarLon = Double.parseDouble(userStarLon);
		this.userEndLon = Double.parseDouble(userEndLon);
		this.userStartAlt = Double.parseDouble(userStartAlt);
		this.userEndAlt = Double.parseDouble(userEndAlt);
		this.save = save;
		this._and = and;
		this._or=or;
		this._device=device;
		this._time=time;
		this._location=location;
		//try {
		//		FileOutputStream fileoutput = new FileOutputStream("/Users/gal/Desktop/1.txt");
		//		ObjectOutputStream out = new ObjectOutputStream(fileoutput);
		//		out.writeObject(filter338);
		//		out.close();
		//		fileoutput.close();
		//		System.err.println("SER ub zibi");
		//	}
		//	catch (IOException i) {
		//		i.printStackTrace();
		//	}
		//}
		if(!save) {
			DBi = CreateDB.FullDB;
		}
		this.userStartTime = userStartTime;
		this.userEndTime = userEndTime;
		this.userDevice = userDevice;
		resultColl = new LinkedList<>();
		if(and) {
			if(time && device) {
				FilterByTime Filtertime = new FilterByTime(userStartTime, userEndTime, DBi, true, null); //TODO : change to
				FilterByDeviceInput Filterdevice = new FilterByDeviceInput(userDevice, Filtertime.getResult(), true, null);
				resultColl = Filterdevice.getResult();
			}

			else if(time && location) {
				FilterByTime Filtertime2 = new FilterByTime(userStartTime, userEndTime, DBi, true, null); //TODO : change to
				FilterByLocation filter_location = new FilterByLocation(this.userStarLat, this.userStarLon, this.userStartAlt, this.userEndLat, this.userEndLon, this.userEndAlt, Filtertime2.getResult(), true, null);
				resultColl = filter_location.get_resultList();
			}

			else if(device && location) {
				FilterByLocation filter_location2 = new FilterByLocation(this.userStarLat, this.userStarLon, this.userStartAlt, this.userEndLat, this.userEndLon, this.userEndAlt, DBi, true, null);
				FilterByDeviceInput Filterdevice2 = new FilterByDeviceInput(userDevice, filter_location2.get_resultList(), true, null);
				resultColl = Filterdevice2.getResult();
			}
		}

		else if(or) {
			if(time || device) {
				FilterByTime FiltertimeOR1 = new FilterByTime(userStartTime, userEndTime, DBi, true, null); //TODO : change to
				FilterByDeviceInput FilterdeviceOR1 = new FilterByDeviceInput(userDevice, DBi, true, null);
				mergerCollection(FiltertimeOR1.getResult(), FilterdeviceOR1.getResult());
			}

			else if(time || location) {
				FilterByTime FiltertimeOR2 = new FilterByTime(userStartTime, userEndTime, DBi, true, null); //TODO : change to
				FilterByLocation filter_locationOR2 = new FilterByLocation(this.userStarLat, this.userStarLon, this.userStartAlt, this.userEndLat, this.userEndLon, this.userEndAlt, DBi, true, null);
				mergerCollection(FiltertimeOR2.getResult(), filter_locationOR2.get_resultList());
			}

			else if(device || location) {
				FilterByLocation filter_locationOR3 = new FilterByLocation(this.userStarLat, this.userStarLon, this.userStartAlt, this.userEndLat, this.userEndLon, this.userEndAlt, DBi, true, null);
				FilterByDeviceInput FilterdeviceOR3 = new FilterByDeviceInput(userDevice, DBi, true, null);
				mergerCollection(filter_locationOR3.get_resultList(), FilterdeviceOR3.getResult());
			}
		}

		else { // only time , device , location
			if(time) {
				FilterByTime FiltertimeALONE = new FilterByTime(userStartTime, userEndTime, DBi, true, null); //TODO : change to
				resultColl = FiltertimeALONE.getResult();
			}
			else if(device) {
				FilterByDeviceInput FilterdeviceALONE = new FilterByDeviceInput(userDevice, DBi, true, null);
				resultColl = FilterdeviceALONE.getResult();
			}
			else if(location) {
				FilterByLocation filter_locationALONE = new FilterByLocation(this.userStarLat, this.userStarLon, this.userStartAlt, this.userEndLat, this.userEndLon, this.userEndAlt , DBi, true, null);
				resultColl = filter_locationALONE.get_resultList();
			}

		}
	}

	public boolean is_time() {
		return _time;
	}

	public void set_time(boolean _time) {
		this._time = _time;
	}

	public boolean is_device() {
		return _device;
	}

	public void set_device(boolean _device) {
		this._device = _device;
	}

	public boolean is_location() {
		return _location;
	}

	public void set_location(boolean _location) {
		this._location = _location;
	}

	public boolean is_and() {
		return _and;
	}

	public void set_and(boolean _and) {
		this._and = _and;
	}

	public boolean is_or() {
		return _or;
	}

	public void set_or(boolean _or) {
		this._or = _or;
	}

	private void mergerCollection(LinkedList<Wifi> arr, LinkedList<Wifi> brr){
		for(Wifi a: arr ) {
			resultColl.add(a);
		}
		for(Wifi a: brr ) {
			resultColl.add(a);
		}

	}

}
