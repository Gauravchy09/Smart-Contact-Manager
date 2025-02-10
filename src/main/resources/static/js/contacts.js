console.log("contacts");

const viewContact = document.getElementById("view_contact_modal");

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

const instanceOptions = {
    id:"view_contact_modal",
    override: true
};

const ContactModal = new Modal(viewContact, options, instanceOptions);  

function openContactModal(){
    ContactModal.show();
}
function closeContactModal(){
    ContactModal.hide();

}
async function loadContactdata(id){
    console.log(id);
    try{
        const data = await (
            await fetch('http://localhost:8081/api/contact/'+id)
        ).json();
        console.log(data);
        document.querySelector('#contact_name').innerHTML = data.name;
        document.querySelector('#contact_email').innerHTML = data.email;
        document.querySelector('#contact_phone').innerHTML = data.phoneNumber;
        document.querySelector('#contact_address').innerHTML = data.address;
        document.querySelector('#contact_image').src = data.picture;
        document.querySelector('#contact_about').innerHTML= data.description;
        const contactFavourite = document.querySelector('#contact_favourite');
        if (data.favourite) {
            contactFavourite.innerHTML = "<i class= 'fas fa-star text-yellow-400'></i><i class=' fas fa-star text-yellow-400'></i><i class= 'fas fa-star text-yellow-400'><i class='fas fa-star text-yellow-400'></i><i class= 'fas fa-star text-yellow-400'>";
        }
        else {
            contactFavourite.innerHTML = "Not a Favourite Contact";
        }
        document.querySelector("#contact_website").href= data.websiteLink;
        document.querySelector('#contact_website').innerHTML = data.websiteLink;
        document.querySelector('#contact_linkedIn').href = data.linkedInLink;
        document.querySelector('#contact_linkedIn').innerHTML = data.linkedInLink;
        openContactModal();
    }
    catch(error){
        console.log("Error: ",error);
    }
    
}

//delete contacts::
async function deleteContact(id) {

    Swal.fire({
        icon: "warning",
        title: "Do you want to delete the Contact?",
        showCancelButton: true,
        confirmButtonText: "Delete",
        confirmButtonColor: "#d33",
        cancelButtonText:"Cancel",
        cancelButtonColor:'#3085d6',

      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            const url = "http://localhost:8081/user/contacts/delete/"+id;
            window.location.replace(url);
          Swal.fire("Deleted!", "", "success");
        } else if (result.isDenied) {
          Swal.fire("Changes are not saved", "", "info");
        }
      });
    
}
