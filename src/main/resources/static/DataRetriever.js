/*
Getting actual weather data for selected country using ajax
*/
function getData() {

    var select = document.getElementById("country_selection");
    var itemId = select.options[select.selectedIndex].id;
    if (itemId === "none") {
        var container = document.getElementById("dataContainer");
        container.innerText = "";
        return;
    }


    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {

            var container = document.getElementById("dataContainer");
            container.innerText = "";
            container.insertAdjacentHTML('beforeend', this.response);
        }
    };
    xhttp.open("GET", "actual/" + itemId, true);
    xhttp.send();
}