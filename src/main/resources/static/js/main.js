const searchForm = document.querySelector("#search-form");
const searchFormInput = document.querySelector("#search-form input");
const info = document.querySelector(".info");

const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

if(SpeechRecognition){
    console.log("Your browser supports speech recognition!");
    searchForm.insertAdjacentHTML("beforeend", '<button type="button"><i class="fas fa-microphone"></i></button>');
    const micBtn = document.querySelector("#search-form button");
    const micIcon = document.querySelector("#search-form i");
    const speechRecognition = new SpeechRecognition();
    micBtn.addEventListener("click",micBtnClick);
    function micBtnClick(){
        if(micIcon.classList.contains("fa-microphone")){
            //    Start speech recognition
            speechRecognition.start();
        }
        else{
            //    Stop speech recognition
            speechRecognition.stop();
        }
    }

    speechRecognition.addEventListener("start", startSpeechRecognition);
    function startSpeechRecognition(){
        micIcon.classList.remove("fa-microphone");
        micIcon.classList.add("fa-microphone-slash");
        searchFormInput.focus();
        console.log("Voice activated, SPEAK");
    }

    speechRecognition.addEventListener("end", endSpeechRecognition);
    function endSpeechRecognition(){
        micIcon.classList.remove("fa-microphone-slash");
        micIcon.classList.add("fa-microphone");
        searchFormInput.focus();
        console.log("Speech recognition service disconnected");
    }

    speechRecognition.addEventListener("result", resultSpeechRecognition);
    function resultSpeechRecognition(event){
        const current = event.resultIndex;
        const transcript = event.results[current][0].transcript;

        if(transcript.toLowerCase().trim()==="stop recording") {
            speechRecognition.stop();
        }
        else if(!searchFormInput.value) {
            searchFormInput.value = transcript;
        }
        else {
            if(transcript.toLowerCase().trim()==="go") {
                searchForm.submit();
            }
            else if(transcript.toLowerCase().trim()==="reset input") {
                searchFormInput.value = "";
            }
            else {
                searchFormInput.value = transcript;
            }
        }
    }

    info.textContent = 'Voice Commands: "stop recording", "reset input", "go"';
}
else{
    console.log("Your Browser does not support speech Recognition");
    info.textContent = "Your Browser does not support Speech Recognition";
}

