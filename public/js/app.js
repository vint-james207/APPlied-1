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
        $('#submit').on('click', function() {
          event.preventDefault();
            var user = {
                username: $('#userName').val(),
                password: $('#password').val()
            }

            $('#logIn').fadeOut(2000, function() {
                $('header').removeClass('hidden');
            })
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

            })
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
        })
    },
    read: function() {
        $.ajax({
            url: "/jobs",
            method: "GET",
            success: function(data) {
                console.log(data);
                data = JSON.parse(data);
            },
            error: function(err) {
                console.error(err);
            }
        })
    },
    update: function() {
        $.ajax({
            url: "/jobs",
            method: "PUT",
            success:function(data) {

            },
            error:function(err) {

            }
        })
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
        })
    }
}
