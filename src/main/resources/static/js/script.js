console.log("script loaded");

let currentTheme=getTheme();
// initialy ->
changeTheme(currentTheme);


function changeTheme(){
    // set to web page
    document.querySelector("html").classList.add(currentTheme);
    // set to listner to change theme
    const changeThemeButton = document.querySelector("#theme_change_button");
    changeThemeButton.querySelector('span').textContent = currentTheme == 'light'? 'Dark' : 'Light';
    changeThemeButton.addEventListener('click', (event)=>{   
        const oldTheme = currentTheme;
        if(currentTheme=="dark")
          currentTheme="light";
        else
         currentTheme="dark";
    // set to local storage
    setTheme(currentTheme);
    document.querySelector("html").classList.remove(oldTheme);
    document.querySelector("html").classList.add(currentTheme);

    //change the text of button
    changeThemeButton.querySelector('span').textContent = currentTheme == 'light'? 'Dark' : 'Light';
    });
}

// set theme to local storage
function setTheme(theme){
    localStorage.setItem("theme", theme);
}

// get theme from local storage
function getTheme(){
    let theme=localStorage.getItem("theme");
    if(theme)
        return theme;
    else
       return "light";
}
