<#-- <link rel="stylesheet" type="text/css" href="/validation/validate.css" /> -->
<link rel="stylesheet" type="text/css" href="/sally/jobad/build/release/JOBAD.css" />

<link rel="stylesheet" type="text/css" href="/sally/tablesorter/blue/style.css" />

<script>
$(function() {

	$("#tbl").tablesorter( { sortList: [[0,0]] } );
	$(".result-row").each(function(o, obj) {
		$(obj).click(function() {
			$.get("/sally/validate", {uri:$(obj).attr("id")}, function() {
			});			
		});
	});
});
</script>

<form class="cbp-mc-form" method="POST">
    
    <div class="cbp-mc-column">
    <label for="incident_id">Incident ID</label>
        <input type="text" id="incident_id" name="incident_id" placeholder="ID" value="${incident_id}">
        
    <label for="incident_description">Incident Description (Summary)</label>
        <textarea text = "incident_description"> ${incident_description}
        </textarea>
        
	<label for="incident_resolution">Incident Resolution </label>
        <textarea text = "incident_resolution"> ${incident_resolution}
        </textarea>
        
     <h4>Incident Details:</h4>   
        
     <label for="incident_businessapp">EADS_CI Business Service</label>
        <input type="text" id="incident_businessapp" name="incident_businessapp" placeholder="businessapp" value="${incident_businessapp}">   
     
     <label for="incident_status">Status</label>
        <input type="text" id="incident_status" name="incident_status" placeholder="status" value="${incident_status}">  
     
     <label for="incident_priority">Priority</label>
        <input type="text" id="incident_priority" name="incident_priority" placeholder="priority" value="${incident_priority}">
        
     <label for="incident_region">Region</label>
        <input type="text" id="incident_region" name="incident_region" placeholder="region" value="${incident_region}">
        
     <label for="incident_servtarget">Service Target</label>
        <input type="text" id="incident_servtarget" name="incident_servtarget" placeholder="servtarget" value="${incident_servtarget}">
     
     <label for="incident_progress">Progress</label>
        <input type="text" id="incident_progress" name="incident_progress" placeholder="progress" value="${incident_progress}">
                 
     <label for="incident_opcateg">Operational Categorization Tier</label>
        <input type="text" id="incident_opcateg" name="incident_opcateg" placeholder="opcateg" value="${incident_opcateg}">
        
     <label for="incident_agroup">Assigned Group</label>
        <input type="text" id="incident_agroup" name="incident_agroup" placeholder="agroup" value="${incident_agroup}">      

	<label for="incident_notes">Incident Notes</label>
        <textarea text = "incident_notes"> ${incident_notes}</textarea>
    </div>
    
    <div class="cbp-mc-column">
        <label>Append to Existing Incident</label>
        <input type="text" id="incident_description" name="incident_description" placeholder="text">
    	Or:
   		<input type="checkbox" name="create_new" value="Incident">Create New Incident with the details above.<br>	
    <div class="cbp-mc-submit-wrap"><input class="cbp-mc-submit" type="submit" value="Save as Draft" />
    <input class="cbp-mc-submit" type="submit" value="Wait for further Approval" />
    <input class="cbp-mc-submit" type="submit" value="Post to SMARTER KM iShare" />
    <input class="cbp-mc-submit" type="submit" value="Save as XML file" />
    </div>  
    </div>
</form>