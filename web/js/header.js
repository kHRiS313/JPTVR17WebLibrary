
document.getElementById("menu1").onclick = function(){
  toogleMenuActive("menu1");
  fetch('listNewBooks')
          .then(status)
          .then(json)
          .then(function(data) {  
            printListNewBooks(data);
            console.log('Request succeeded with JSON response', data);  
          })
          .catch(function(error) {  
            console.log('Request failed', error);  
          });

  
};
document.getElementById("menu2").onclick = function(){
  toogleMenuActive("menu2");
};
document.getElementById("menu3").onclick = function(){
  toogleMenuActive("menu3");
};
document.getElementById("menu4").onclick = function(){
  toogleMenuActive("menu4");
};
function toogleMenuActive(elementId){
  let activeElement = document.getElementById(elementId);
  let passiveElements = document.getElementsByClassName("nav-item");
  for(let i = 0; i < passiveElements.length; i++){
    if(activeElement === passiveElements[i]){
      passiveElements[i].classList.add("active");
    }else{
      if(passiveElements[i].classList.contains("active")){
        passiveElements[i].classList.remove("active");
      }
    }
  }
}


function status(response) {  
  if (response.status >= 200 && response.status < 300) {  
    return Promise.resolve(response)  
  } else {  
    return Promise.reject(new Error(response.statusText))  
  }  
}
function json(response) {  
  return response.json()  
}
function printListNewBooks(data){
  let cards = '';
  for(let i=0;i<data.length;i++){
    cards+=
     `<div class="card border-primary mb-3" style="max-width: 10rem;">
        <div class="card-header">${data[i].name}</div>
        <div class="card-body">
          <h4 class="card-title">${data[i].author}</h4>
          <p class="card-text">${data[i].publishedYear}</p>
        </div>
      </div>`;
  }
  document.getElementById('content').innerHTML = cards;
}
