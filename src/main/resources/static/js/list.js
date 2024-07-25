// function updateChildrenAgesFields() {
//     var numberOfChildren = document.getElementById("children").value;
//     var childrenAgesContainer = document.getElementById("children-ages-container");
//
//     // Clear existing age fields
//     childrenAgesContainer.innerHTML = '';
//
//     // Add new age fields based on the number of children
//     for (var i = 0; i < numberOfChildren; i++) {
//         var div = document.createElement("div");
//         div.className = "form-group col-md-2";
//
//         var label = document.createElement("label");
//         label.setAttribute("for", "childAge" + (i + 1));
//         label.innerText = "Age of Child " + (i + 1);
//
//         var input = document.createElement("input");
//         input.type = "number";
//         input.className = "form-control";
//         input.id = "childAge" + (i + 1);
//         input.name = "childrenAges";
//         input.placeholder = "Age";
//         input.min = 1;
//         input.max = 17;
//         input.required = true;
//
//         div.appendChild(label);
//         div.appendChild(input);
//         childrenAgesContainer.appendChild(div);
//     }
//
//     if (numberOfChildren > 0) {
//         childrenAgesContainer.style.display = 'flex';
//     } else {
//         childrenAgesContainer.style.display = 'none';
//     }
// }

function updateChildrenAgesField() {
    var numberOfChildren = document.getElementById("children").value;
    var childrenAgesGroup = document.getElementById("children-ages-group");
    var childrenAgesInput = document.getElementById("childrenAges");

    if (numberOfChildren > 0) {
        childrenAgesGroup.style.display = 'block';

        var placeholderText = `Enter age${numberOfChildren > 1 ? 's (comma-separated)' : ''} of ${numberOfChildren} child${numberOfChildren > 1 ? 'ren' : ''}`;
        childrenAgesInput.placeholder = placeholderText;
    } else {
        childrenAgesGroup.style.display = 'none';
    }
}