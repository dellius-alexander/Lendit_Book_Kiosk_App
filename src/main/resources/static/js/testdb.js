async function runMethod() {
    let prompt_response = window.prompt("Type [POST]|[GET]|[PUT]|[DELETE] to select your request choice: ");
    const uri = new URL("http://localhost:8081")
    if (prompt_response == null) {
        // provide message and timeout
        setTimeout(() => {
            alert("Please try again!");
        }, 3000);
        await runMethod();
    }
    switch (prompt_response) {
        case ('get' | 'GET'):

            await fetch(uri)
                .then(response => response.json())
                .then(data => console.log(data))
            break;
        default:
            break;
    }
// // there are many ways to use the prompt feature
// prompt_response = window.prompt(); // open the blank prompt window
// prompt_response = prompt();       //  open the blank prompt window
// prompt_response = window.prompt('Are you feeling lucky'); // open the window with Text "Are you feeling lucky"
// prompt_response = window.prompt('Are you feeling lucky', 'sure'); // open the window with Text "Are you feeling lucky" and default value "sure"
}
await runMethod();