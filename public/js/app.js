
$(document).ready(function() {

  apply.init();
});

var apply = {
    init: function() {
        apply.events();
        apply.styling();
    },
    styling: function() {

    },
    events: function() {
        $('#logInButton').on('click', function() {
          event.preventDefault();
            var user = {
                username: $('#userName').val(),
                password: $('#password').val()
            };

            $('#logIn').fadeOut(2000, function() {
                $('header').removeClass('hidden');
            });
            console.log(JSON.stringify(user));
            $.ajax({
                method: "POST",
                url: "/login",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(user),
                success: function(data){
                  console.log("this is my login",data);
                },
                error:function(err){
                  console.error("something went wrong",err);
                }

            });

          //  apply.read();
        });

        $('#apply').on('click', function(){
          event.preventDefault();
          var jobs = {
            companyName: $('input[name = "company"]').val(),
            salary: $('input[name = "salary"]').val(),
            applied:  true,
            location:  $('input[name = "location"]').val(),
            contactName:  $('input[name = "contactName"]').val(),
            contactNumber:  $('input[name = "phone"]').val(),
            contactEmail:  $('input[name = "email"]').val(),
            comments:  $('textarea[name = "comments"]').val()
          };
          console.log(jobs);
          apply.create(JSON.stringify(jobs));
          //apply.read();
        });

        $('#append').on('click', function(){
           apply.read();
        })
    },


    create: function(applyData) {
        $.ajax({
            url: "/jobs",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            data: applyData,
            success: function(data) {
                console.log(data);
            },
            error: function(err) {
                console.error(err);
            }
        });
    },
    read: function() {
        $.ajax({
            url: "/jobs",
            method: "GET",
            success: function(data) {
                console.log(data);
                data = JSON.parse(data);
                data.forEach(function(item){
                  console.log('this is the item',item);
                  $('#appliedTo ul').append(`<li><fieldset>${item.companyName} </br> ${item.salary} </br> ${item.location} </br> ${item.contactName} </br> ${item.contactNumber}
                    </br> ${item.contactEmail} </br> ${item.comments}</fieldset>`);
                });
            },
            error: function(err) {
                console.error(err);
            }
        });
    },
    update: function() {
        $.ajax({
            url: "/jobs",
            method: "PUT",
            success:function(data) {

            },
            error:function(err) {

            }
        });
    },
    destroy: function() {
        $.ajax({
            url: "/jobs",
            method: "DELETE",
            success:function(data) {

            },
            error: function(err) {
                console.error(err);
            }
        });
    }
};
