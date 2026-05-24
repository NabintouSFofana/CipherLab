/* ============================================================
   CIPHER LAB
   Interactive Caesar Cipher + Cryptanalysis
============================================================ */

(() => {

'use strict'

/*──────────────────────────────────────────────
Algorithm
──────────────────────────────────────────────*/

function caesarEncrypt(
text,
shift
){

if(
text==null
){

throw new TypeError(
'text must not be null'
)

}

const n=
(
(
shift%26
)+26
)%26

let out=''

for(
const c
of text
){

const code=
c.charCodeAt(
0
)

if(
code>=65
&&
code<=90
){

out+=
String.fromCharCode(

65+

(
code-65+n
)%26

)

}

else if(
code>=97
&&
code<=122
){

out+=
String.fromCharCode(

97+

(
code-97+n
)%26

)

}

else{

out+=c

}

}

return out

}

/*──────────────────────────────────────────────
DOM
──────────────────────────────────────────────*/

const $=
id=>
document.getElementById(
id
)

const plainInput=
$('plainInput')

const shiftInput=
$('shiftInput')

const shiftDisplay=
$('shiftDisplay')

const cipherOutput=
$('cipherOutput')

const copyBtn=
$('copyBtn')

const swapBtn=
$('swapBtn')

const toast=
$('toast')

const toastText=
$('toastText')

const breakBtn=
$('breakBtn')

const breakResults=
$('breakResults')

const innerRing=
$('innerRing')

const wheelSvg=
$('wheelSvg')

/*──────────────────────────────────────────────
Wheel
──────────────────────────────────────────────*/

const SVG_NS=
'http://www.w3.org/2000/svg'

const ALPHABET=
'ABCDEFGHIJKLMNOPQRSTUVWXYZ'

function placeLettersOnRing(
parent,
radius,
klass
){

for(
let i=0;
i<26;
i++
){

const angle=
(
i/26
)
*
360
-
90

const rad=
angle*
Math.PI/
180

const x=
Math.cos(
rad
)
*
radius

const y=
Math.sin(
rad
)
*
radius

const t=
document.createElementNS(
SVG_NS,
'text'
)

t.setAttribute(
'x',
x
)

t.setAttribute(
'y',
y
)

t.setAttribute(
'class',
`letter ${klass}`
)

t.dataset.index=i

t.textContent=
ALPHABET[
i
]

parent.appendChild(
t
)

}

}

placeLettersOnRing(
wheelSvg,
95,
'outer'
)

placeLettersOnRing(
innerRing,
65,
'inner'
)

function rotateWheel(
shift
){

const deg=
(
(
shift%26
)+26
)%26
*
(
360/26
)

innerRing.setAttribute(

'transform',

`rotate(${deg})`

)

}

/*──────────────────────────────────────────────
Stats
──────────────────────────────────────────────*/

function updateStats(){

const chars=
plainInput.value.length

document.title=
`CipherLab • ${chars} chars`

}

/*──────────────────────────────────────────────
Render
──────────────────────────────────────────────*/

function render(){

const text=
plainInput.value

const shift=
parseInt(
shiftInput.value
)
||
0

shiftDisplay.textContent=
`${shift>0?'+':''}${shift}`

cipherOutput.value=
caesarEncrypt(
text,
shift
)

rotateWheel(
shift
)

highlight(
text
)

updateStats()

}

/*──────────────────────────────────────────────
Highlight
──────────────────────────────────────────────*/

function highlight(
text
){

wheelSvg
.querySelectorAll(
'.match'
)
.forEach(

e=>

e.classList.remove(
'match'
)

)

for(
const c
of text
){

const code=
c.charCodeAt(
0
)

let idx=-1

if(
code>=65
&&
code<=90
)
idx=
code-65

if(
code>=97
&&
code<=122
)
idx=
code-97

if(
idx>=0
){

wheelSvg
.querySelector(

`.outer[data-index="${idx}"]`

)
?.classList.add(
'match'
)

innerRing
.querySelector(

`.inner[data-index="${idx}"]`

)
?.classList.add(
'match'
)

return

}

}

}

/*──────────────────────────────────────────────
Break Cipher
──────────────────────────────────────────────*/

breakBtn
?.addEventListener(

'click',

()=>{

const text=
cipherOutput
.value
.trim()

if(
!text
){

showToast(
'Generate ciphertext first'
)

return

}

breakResults.innerHTML=''

const words=[

'THE',
'THIS',
'HELLO',
'YOU',
'SECRET',
'IS',
'ARE'

]

for(
let i=0;
i<26;
i++
){

const candidate=
caesarEncrypt(
text,
-i
)

let score=0

words.forEach(

w=>{

if(

candidate
.toUpperCase()

.includes(
w
)

)

score++

}

)

const card=
document.createElement(
'div'
)

card.className=
'break-card'

if(
score>0
){

card.classList.add(
'break-best'
)

}

card.innerHTML=
`

<strong>

Shift ${i}

</strong>

<br>

${candidate}

${
score
?
'<br><small>✓ probable</small>'
:
''
}

`

breakResults.appendChild(
card
)

}

showToast(
'Generated 26 possibilities'
)

}

)

/*──────────────────────────────────────────────
Events
──────────────────────────────────────────────*/

plainInput
.addEventListener(
'input',
render
)

shiftInput
.addEventListener(
'input',
render
)

document
.querySelectorAll(
'.chip'
)
.forEach(

btn=>{

btn.addEventListener(

'click',

()=>{

shiftInput.value=
btn.dataset.shift

render()

}

)

}

)

swapBtn
.addEventListener(

'click',

()=>{

plainInput.value=
cipherOutput.value

shiftInput.value=
-
parseInt(
shiftInput.value
)

render()

}

)

copyBtn
.addEventListener(

'click',

async()=>{

await navigator
.clipboard
.writeText(

cipherOutput.value

)

showToast(
'Copied'
)

}

)

/*──────────────────────────────────────────────
Toast
──────────────────────────────────────────────*/

let timer

function showToast(
msg
){

toastText.textContent=
msg

toast.hidden=
false

toast.classList.add(
'is-visible'
)

clearTimeout(
timer
)

timer=
setTimeout(

()=>{

toast.classList.remove(
'is-visible'
)

toast.hidden=true

},

1800

)

}

/*──────────────────────────────────────────────
Boot
──────────────────────────────────────────────*/

render()

})()