const annualPremium = document.getElementById("annualPremium");
const postedPremium = document.getElementById("postedPremium");
const rate = document.getElementById("rate");
const sumInsured = document.getElementById("sumInsured");
const inceptionDate = new Date(document.getElementById("inceptionDate").value);
const expiryDate = new Date(document.getElementById("expiryDate").value);
const diff = Math.abs(expiryDate.getTime() - inceptionDate.getTime());



function DateDiff(date1, date2) {
    date1.setHours(0);
    date1.setMinutes(0, 0, 0);
    date2.setHours(0);
    date2.setMinutes(0, 0, 0);
    var datediff = Math.abs(date1.getTime() - date2.getTime()); // difference 
    return parseInt(datediff / (24 * 60 * 60 * 1000), 10);      
}
function logic() {
	annualPremium.value = sumInsured.value * rate.value / 100;
	postedPremium.value = sumInsured.value * DateDiff(expiryDate, inceptionDate) / 365;
}
