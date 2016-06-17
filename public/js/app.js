$(document).ready(function() {
  $('#submit').on('click', function() {
      $('#logIn').fadeOut(2000, function() {

          $('header').removeClass('hidden');
      })
  })

});
//
// // var apply = {
// //     init: function(){
//          apply.events();
//          apply.styling();
//        },
//        styling: function(){
//
//        },
// //     events: function() {
// //       $('#submit').on('click', function() {
// //           $('#logIn').fadeOut(2000, function() {
// //
// //               $('header').removeClass('hidden');
// //           })
// //       })
// //     },
//
//       create: function(applyData){
//         $.ajax({
//           url: "/update-job",
//           method: "POST",
//           data: applyData,
//           success: function(data){
//             console.log(data);
//           },
//           error: function(err){
//             console.error(err);
//           }
//         })
//       },
//       read: function(){
//         $.ajax({
//           url: "/update-job",
//           method: "GET",
//           success: function(data){
//             console.log(data);
//             data = JSON.parse(data);
//           },
//           error: function(err){
//             console.error(err);
//           }
//         })
//       },
//       update: function(){
//         $.ajax({
//           url: "/update-job",
//           method: "PUT"
//           success function(data){
//
//           },
//           error function(err){
//
//           }
//         })
//       },
//       destroy: function(){
//         $.ajax({
//           url: "/update-job",
//           method: "DELETE"
//           success function(data){
//
//           },
//           error: function(err){
//             console.error(err);
//           }
//         })
//       }
// // }
