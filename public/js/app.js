$(document).ready(function() {
    $('#submit').on('click', function() {
        $('#logIn').fadeOut(2000, function() {

            $('header').removeClass('hidden');
        })
    })

});
