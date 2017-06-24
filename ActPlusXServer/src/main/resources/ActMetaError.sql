update ActivityMeta set actType = (select actType from Activity where actId = 11),
	actName = (select actName from Activity where actId = 11),
	actLoc = (select actLoc from Activity where actId = 11),
	actTime = (select actTime from Activity where actId = 11)

update ActivityMeta as am, Activity as a 
	set 
	am.actType = a.actType,
	am.actName = a.actName,
	am.actLoc = a.actLoc,
	am.actTime = a.actTime
where am.actId = a.actId;
