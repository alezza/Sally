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

<form>

<form class="cbp-mc-form">
    <div class="cbp-mc-column">
    <label for="incident_description">Incident ID</label>
        <input type="text" id="incident_id" name="incident_id" placeholder="ID">
    <label for="incident_description">Incident Description (Summary)</label>
        <input type="text" id="incident_description" name="incident_description" placeholder="text">
	<label for="incident_resolution">Incident Resolution </label>
        <input type="text" id="incident_resolution" name="incident_resolution" placeholder="text">
	 <label for="application">Application</label>
        <select id="application" name="application">
            <option>CI Business Service</option>
            <option>CI+</option>
        </select>   
     <label for="tag">Tags</label>
        <select id="tag" name="tag">
            <option>Status</option>
            <option>Priority</option>
            <option>Region</option>
            <option>Service Target</option>
            <option>Progress</option>
        </select>
    </div>
    <div class="cbp-mc-column">
        <label>Append to Existing Incident</label>
        <input type="text" id="incident_description" name="incident_description" placeholder="text">
    	Or:
   		<input type="checkbox" name="create_new" value="Incident">Create New Incident with the details above.<br>	
    <div class="cbp-mc-submit-wrap"><input class="cbp-mc-submit" type="submit" value="Save as Draft" />
    <input class="cbp-mc-submit" type="submit" value="Wait for further Approval" />
    <input class="cbp-mc-submit" type="submit" value="Post to SMARTER KM iShare" />
    </div>  
</form>