package com.Rest2Go;

class RestData{
	public String name;
	public String Address;
	public String phone;
	public String phone2;
	
	public RestData(String _name, String _Address, String _phone, String _phone2 )
	{
		this.name=_name;
		this.Address = _Address;
		if (_phone!=null)
			this.phone = _phone;
		if (_phone2!=null)
			this.phone2 = _phone2;
		
	}
	
}