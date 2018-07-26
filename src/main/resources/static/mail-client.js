$(document).ready(function () {
    var submitter;
    $('input:submit').click(function () {
        submitter = $(this).val()
    });

    $("#email-form").submit(function (event) {

        // Don't submit the form normally
        event.preventDefault();

        var $form = $(this),
            recipient = $form.find("input[name='recipient']").val(),
            message = $form.find("input[name='message']").val();

        // Compose the data in the format that the API is expecting
        var data = {recipient: recipient, message: message};
        if (submitter === 'Send') {
            // Send one email
            console.log("send 1 mail");
            // Send the data using post
            $.ajax({
                url: '/mail/send',
                type: 'POST',
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (result) {
                    if(result) {
                        console.log("success");
                        $('.result-message').empty().append("The email was successfully sent! Congratulations!");
                        $("#email-form").find("input[name='recipient']").val("");
                        $("#email-form").find("input[name='message']").val("");
                    } else $('.result-message').empty().append("Ooops! Something went wrong. Please try again!");
                }
            });
        } else if (submitter === 'Spam') {
            // Spamming e-mails
            $.ajax({
                url: '/mail/spam',
                type: 'POST',
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (result) {
                    if (result) {
                        $('.result-message').empty().append("Server starts spamming email! Click Cancel to stop.");
                        $("#email-form").find("input[name='recipient']").prop('readonly', true);;
                        $("#email-form").find("input[name='message']").prop('readonly', true);;
                        $("#email-form").find("input[name='send']").prop('disabled', true);
                        $("#email-form").find("input[name='spam']").val("Cancel");
                    } else $('.result-message').empty().append("Ooops! Something went wrong. Please try again!");
                }
            });
        } else if (submitter === 'Cancel') {
            $.ajax({
                url: "http://localhost:8080/mail/cancel"
            }).then(function () {
                // Cleans the form
                $("#email-form").find("input[name='recipient']").val("");
                $("#email-form").find("input[name='message']").val("");

                $("#email-form").find("input[name='spam']").val("Spam");

                $("#email-form").find("input[name='recipient']").prop("readonly", false);
                $("#email-form").find("input[name='message']").prop("readonly", false);

                $("#email-form").find("input[name='send']").prop('disabled', false);

                $('.result-message').empty().append("You have canceled spamming e-mail");
            });
        } else return false;
    });
});