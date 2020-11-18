package com.ctlb.sbtracker


// A class which represents the format of each bus record
class Bus{
    var phn = ""
    var busno = ""
    var drvname = ""
    var type = "B"
    var long = 0.0
    var lat = 0.0
    var status = "I"

    constructor(phn : String,busno : String,drvname : String,long : Double,lat: Double,status: String)
    {
        this.phn = phn
        this.busno = busno
        this.drvname = drvname
        this.long = long
        this.lat = lat
        this.status = status
    }
}