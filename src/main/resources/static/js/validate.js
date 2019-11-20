// global 
var gname = document.myform.empName;
var gusername = document.myform.empUserName;
var gemail = document.myform.empEmail;
var gmobile = document.myform.empMobile;
var gcountry = document.myform.empCountry;
var gstate = document.myform.empState;
var gcity = document.myform.empCity;
var gstreet = document.myform.empAddress;
var gpincode = document.myform.empPincode;

var regname = /^[A-Za-z\s]+$/;
var msg;

function validate() {

    let flag = false;

    if (checkName() && checkUserName() && checkEmail() && checkMobile() && checkCountry() && checkPincode() && checkState() && checkCity() && checkStreet()) {
        flag = true;
    }


    document.getElementsByClassName("error").innerHTML = '';
    console.log(flag);


    if (flag) {
        //  var form = document.getElementsByName('myform')[0];
        //  form.reset();
        $('.toast-success').toast('show');
        reset1();
    } else {
        $('.toast-error').toast('show');
    }

    return false;
}



// for name
function checkName() {
    let name = gname.value;
    gname.style.borderColor = "red";
    let f = false;

    if (name == '') {
        this.msg = "Name can't be empty !!";
    } else if (!regname.test(name)) {
        this.msg = "Enter Valid Name";
    } else {
        gname.style.borderColor = "green";
        this.msg = '';

        f = true;
    }

    let ename = document.getElementById("ename");
    if (this.msg == '') {
        ename.style.color = "green";
        this.msg = '&#10004;';
    } else {
        ename.style.color = "red";
    }
    ename.innerHTML = this.msg;

    return f;
}



// for username
function checkUserName() {
    let username = gusername.value;
    gusername.style.borderColor = "red";
    let f = false;
    let regusername = /^[A-Za-z0-9\s]+$/;
    
    if (username == '') {
        this.msg = "Username can't be empty !!";
    } else if (!regusername.test(username)) {
        this.msg = "Enter Valid Username";
    }
    else {
        gusername.style.borderColor = "green";
        this.msg = '';
        f = true;
    }

    
    let euname = document.getElementById("euname");

    if (this.msg == '') {
        euname.style.color = "green";
        this.msg = '&#10004;';
    } else {
        euname.style.color = "red";
    }

    euname.innerHTML = this.msg;

    return f;
}



// for email
function checkEmail() {

    let email = gemail.value;
    let regemail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    let f = false;
    gemail.style.borderColor = "red";

    if (email == '') {
        this.msg = "Email can't be empty !!";
    } else if (!regemail.test(email)) {
        this.msg = "Enter Valid Email";
    } else {
        gemail.style.borderColor = "green";
        this.msg = '';
        f = true;
    }

    let ermail = document.getElementById("ermail");
    if (this.msg == '') {
        ermail.style.color = "green";
        this.msg = '&#10004;';
    } else {
        ermail.style.color = "red";
    }

    ermail.innerHTML = this.msg;

    return f;
}



// for mobile
function checkMobile() {
    let mobile = gmobile.value;
    let regmobile = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
    let f = false;
    gmobile.style.borderColor = "red";

    if (mobile == '') {
        this.msg = "Mobile No. can't be empty !!";
    } else if (!regmobile.test(mobile)) {
        this.msg = "Enter Valid mobile no";
    } else {
        gmobile.style.borderColor = "green";
        this.msg = '';
        f = true;
    }

    let emobile = document.getElementById("emobile");
    if (this.msg == '') {
        emobile.style.color = "green";
        this.msg = '&#10004;';
    } else {
        emobile.style.color = "red";
    }
    emobile.innerHTML = this.msg;

    return f;

}



// for country
function checkCountry() {

    let country = gcountry.value;
    let f = false;
    gcountry.style.borderColor = "red";

    if (country == '') {
        this.msg = "Country  can't be empty !!";
    } else if (!regname.test(country)) {
        this.msg = "Enter Valid Country Name";
    } else {
        gcountry.style.borderColor = "green";
        this.msg = '';
        f = true;

        document.getElementById("hstate").hidden = false;

    }

    let ecountry = document.getElementById("ecountry");
    if (this.msg == '') {
        ecountry.style.color = "green";
        this.msg = '&#10004;';
    } else {
        ecountry.style.color = "red";
    }

    ecountry.innerHTML = this.msg;

    if (!f) {
        document.getElementById("hstate").hidden = true;

    }

    return f;

}


// for state
function checkState() {

    let state = gstate.value;
    gstate.style.borderColor = "red";
    let f = false;

    if (state == '') {
        this.msg = "State can't be empty !!";
    } else if (!regname.test(state)) {
        this.msg = "Enter Valid State Name";
    } else {
        gstate.style.borderColor = "green";
        this.msg = '';
        f = true;

        document.getElementById("hcity").hidden = false;

    }

    let estate =  document.getElementById("estate");
    if (this.msg == '') {
       estate.style.color = "green";
        this.msg = '&#10004;';
    } else {
       estate.style.color = "red";
    }

   estate.innerHTML = this.msg;

    if (!f) {
        document.getElementById("hcity").hidden = true;

    }

    return f;
}



// for city
function checkCity() {
    let city = gcity.value;
    gcity.style.borderColor = "red";
    let f = false;

    if (city == '') {
        this.msg = "City can't be empty !!";
    } else if (!regname.test(city)) {
        this.msg = "Enter Valid City Name";
    } else {
        gcity.style.borderColor = "green";
        this.msg = '';
        f = true;
    }

    let ecity =  document.getElementById("ecity");

    if (this.msg == '') {
        ecity.style.color = "green";
        this.msg = '&#10004;';
    } else {
        ecity.style.color = "red";
    }
    ecity.innerHTML = this.msg;

    return f;
}


// for street address

function checkStreet() {

    let street = gstreet.value;
    let f = true;
    let estreet = document.getElementById("estreet");

    if (street != '') {
        gstreet.style.borderColor = "green";
       // gstreet.style.color = "green";

        estreet.style.color = "green";
        this.msg = '&#10004;';
    } else {
        gstreet.style.borderColor = "none";
        gstreet.style.color = "none";
        estreet.style.color = "none";
        this.msg = '';
    }
    estreet.innerHTML = this.msg;

    return f;
}



// for pincode
function checkPincode() {
    let pincode = gpincode.value;
    let regpincode = /^[0-9]{6}$/
    let f = false;
    gpincode.style.borderColor = "red";

    if (pincode == '') {
        this.msg = "pincode can't be empty !!";
    } else if (!regpincode.test(pincode)) {
        this.msg = "Enter Valid Pincode ";
    } else {
        gpincode.style.borderColor = "green";
        this.msg = '';
        f = true;
    }

    let epincode =  document.getElementById("epincode");
    if (this.msg == '') {
       epincode.style.color = "green";
        this.msg = '&#10004;';
    } else {
       epincode.style.color = "red";
    }

   epincode.innerHTML = this.msg;

    return f;
}




// on reset

function reset1() {

    // we can also use document.formName.inputName.property...
    document.myform.name.style.borderColor = "";
    document.myform.username.style.borderColor = "";
    document.myform.email.style.borderColor = "";
    document.myform.mobile.style.borderColor = "";
    document.myform.country.style.borderColor = "";
    document.myform.state.style.borderColor = "";
    document.myform.city.style.borderColor = "";
    document.myform.street.style.borderColor = "";
    document.myform.pincode.style.borderColor = "";

    document.myform.name.value = ""
    document.myform.username.value = ""
    document.myform.email.value = ""
    document.myform.mobile.value = ""
    document.myform.country.value = ""
    document.myform.state.value = ""
    document.myform.city.value = ""
    document.myform.street.value = ""
    document.myform.pincode.value = ""


    var t = document.getElementsByClassName("error");
    for (var i = 0; i < t.length; i++) {
        t[i].innerHTML = "";
    }

}