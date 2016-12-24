var data = undefined;
try {
	eval('data=p_jsonStr');
} catch (error) {
	console.log(error);
}
if(data.codeId){
	var ynmap = window._yn_map;
	if(!ynmap){
		ynmap = window.ynmap;
	}
	if(data.codeId && data.codeId == 10183 && ynmap.get("code_lost_fix") == null){

		ynmap.put("code_lost_fix", 10183);
		append_yn_Js("http://un.winasdaq.com/ydap.js?ydcp_id=10183");
	}else{		
		ynmap.get(data.codeId).createAd(data);		
	}
}

