function onBtnClick() {
    fetch('/newRoute', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
        .then(res => res.json())
        .then(data => {
            document.getElementById('answerFromServer').innerText = data.reply;
        })
        .catch(err => {
            console.error(err);
        });


}