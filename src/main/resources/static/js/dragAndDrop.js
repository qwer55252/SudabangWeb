var sec9 = document.querySelector('#ex9');
var btnUpload = sec9.querySelector('.btn-upload');
var inputFile = sec9.querySelector('input[type="file"]');
var uploadBox = sec9.querySelector('.upload-box');

/* 박스 안에 Drag 들어왔을 때 */
uploadBox.addEventListener('dragenter', function(e) {
    console.log('dragenter');
});

/* 박스 안에 Drag를 하고 있을 때 */
uploadBox.addEventListener('dragover', function(e) {
    e.preventDefault();

    var valid = e.dataTransfer.types.indexOf('Files') >= 0;

    if(!valid){
        this.style.backgroundColor = 'red';
    }
    else {
        this.style.backgroundColor = 'green';
    }

    console.log('dragover');

    this.style.backgroundColor = 'green';
});

/* 박스 밖으로 Drag가 나갈 때 */
uploadBox.addEventListener('dragleave', function(e) {
    console.log('dragleave');

    this.style.backgroundColor = 'white';
});

/* 박스 안에서 Drag를 Drop했을 때 */
uploadBox.addEventListener('drop', function(e) {
    e.preventDefault();

    console.log('drop');
    this.style.backgroundColor = 'white';

    console.dir(e.dataTransfer);

    var data = e.dataTransfer.files[0];
    console.dir(data);
});
