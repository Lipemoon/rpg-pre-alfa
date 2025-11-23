// ========================
// FUNÇÃO PARA TROCAR TELAS
// ========================
function mostrarTela(id) {
  document.querySelectorAll('.tela').forEach(tela => tela.classList.remove('ativa'));
  document.getElementById(id).classList.add('ativa');
}

// ========================
// BOTÕES DO MENU
// ========================
document.getElementById('btn-jogar').addEventListener('click', () => { 
  mostrarTela('tela-atributos'); 
});
document.getElementById('btn-creditos').addEventListener('click', () => mostrarTela('tela-credito'));
document.getElementById('btn-voltar-menu-credito').addEventListener('click', () => mostrarTela('tela-menu'));
document.getElementById('btn-voltar-menu-jogo').addEventListener('click', () => mostrarTela('tela-menu'));

// ========================
// DISTRIBUIÇÃO DE ATRIBUTOS
// ========================
const atributos = { forca: 10, magia: 10, vida: 10 };
let pontosRestantes = 15;

const pontosSpan = document.getElementById('pontos-restantes');

function atualizarTelaAtributos() {
  document.getElementById('forca').textContent = atributos.forca;
  document.getElementById('magia').textContent = atributos.magia;
  document.getElementById('vida').textContent = atributos.vida;
  pontosSpan.textContent = pontosRestantes;
}

document.querySelectorAll('.mais').forEach(btn => {
  btn.addEventListener('click', () => {
    const attr = btn.dataset.attr;
    if(pontosRestantes > 0){
      atributos[attr]++;
      pontosRestantes--;
      atualizarTelaAtributos();
    }
  });
});

document.querySelectorAll('.menos').forEach(btn => {
  btn.addEventListener('click', () => {
    const attr = btn.dataset.attr;
    if(atributos[attr] > 10){
      atributos[attr]--;
      pontosRestantes++;
      atualizarTelaAtributos();
    }
  });
});


let idJogador;
let idInimigo;
document.getElementById('btn-iniciar-jogo').addEventListener('click', async () => {
  if(pontosRestantes > 0){
    alert('Você deve distribuir todos os 15 pontos antes de iniciar o jogo!');
    return;
  }
  idJogador = await criarJogador(atributos.forca, atributos.vida, atributos.magia);
  idInimigo = await criarInimigo();
  iniciarJogo(); 
  mostrarTela('tela-jogo');
});

atualizarTelaAtributos();

// ========================
// MAPA - Personagem e inimigo
// ========================
const personagem = document.getElementById('personagem');
const inimigo = document.getElementById('inimigo');
const bloco = window.innerWidth / 64;
let posX=100,posY=200;
let inimigoX=500,inimigoY=200;
let movimentoAtual=0, passosAndados=0;
let emBatalha=false;

// Inimigo se move em sequência
const inimigoMovimentos = [
  {passos:5,direcao:-1},
  {passos:6,direcao:1},
  {passos:5,direcao:1},
  {passos:6,direcao:-1}
];

const velocidadeInimigo=200; // ms

function iniciarJogo(){
  const centerX = window.innerWidth / 2;
  const centerY = window.innerHeight / 2;

  posX = centerX - 10 * bloco;
  posY = centerY;
  personagem.style.left = posX+'px';
  personagem.style.top = posY+'px';

  inimigoX = centerX + 10 * bloco;
  inimigoY = centerY;
  inimigo.style.left = inimigoX+'px';
  inimigo.style.top = inimigoY+'px';

  movimentoAtual = 0;
  passosAndados = 0;
  emBatalha=false;

  atualizarHP(); // chama aqui para aparecer barras no início
}

// Atualiza posições
function atualizarPosicoes() {
  personagem.style.left = posX+'px';
  personagem.style.top = posY+'px';
  inimigo.style.left = inimigoX+'px';
  inimigo.style.top = inimigoY+'px';
}

// ========================
// MOVIMENTO CONTÍNUO DO JOGADOR
// ========================
const teclasPressionadas = {};
document.addEventListener('keydown', e => { teclasPressionadas[e.key.toLowerCase()] = true; });
document.addEventListener('keyup', e => { teclasPressionadas[e.key.toLowerCase()] = false; });
const velocidadeJogador = 4;

function moverJogador(){
  if(!emBatalha){
    if(teclasPressionadas['w']) posY -= velocidadeJogador;
    if(teclasPressionadas['s']) posY += velocidadeJogador;
    if(teclasPressionadas['a']) posX -= velocidadeJogador;
    if(teclasPressionadas['d']) posX += velocidadeJogador;

    posX = Math.max(0, Math.min(posX, window.innerWidth-16));
    posY = Math.max(0, Math.min(posY, window.innerHeight-20));

    atualizarPosicoes();
    checarColisao();
  }
  requestAnimationFrame(moverJogador);
}
requestAnimationFrame(moverJogador);

// ========================
// MOVIMENTO DO INIMIGO
// ========================
function atualizarInimigo(){
  const mov = inimigoMovimentos[movimentoAtual];
  inimigoY += mov.direcao*bloco;
  passosAndados++;
  inimigo.style.top = inimigoY+'px';
  checarColisao();
  if(passosAndados>=mov.passos){
    passosAndados=0;
    movimentoAtual=(movimentoAtual+1)%inimigoMovimentos.length;
  }
}
setInterval(atualizarInimigo, velocidadeInimigo);

// ========================
// COLISÃO COM INIMIGO
// ========================
function checarColisao() {
  if(!document.getElementById('tela-jogo').classList.contains('ativa')) return;
  if(emBatalha) return;

  const larguraPersonagem = personagem.offsetWidth;
  const alturaPersonagem = personagem.offsetHeight;
  const larguraInimigo = inimigo.offsetWidth;
  const alturaInimigo = inimigo.offsetHeight;

  const dx = (posX + larguraPersonagem/2) - (inimigoX + larguraInimigo/2);
  const dy = (posY + alturaPersonagem/2) - (inimigoY + alturaInimigo/2);

  const distanciaX = (larguraPersonagem + larguraInimigo) / 2;
  const distanciaY = (alturaPersonagem + alturaInimigo) / 2;

  if(Math.abs(dx) < distanciaX && Math.abs(dy) < distanciaY){
    emBatalha = true;
    piscarTela(() => {
      mostrarTela('tela-batalha');
      iniciarBatalha();
    });
  }
}

function piscarTela(callback){
  let count=0;
  const intervalo=setInterval(()=>{
    document.getElementById('tela-jogo').style.backgroundColor=(count%2===0)?'white':'#4CAF50';
    count++;
    if(count>5){
      clearInterval(intervalo);
      document.getElementById('tela-jogo').style.backgroundColor='#4CAF50';
      callback();
    }
  },100);
}

// ========================
// TELA DE BATALHA
// ========================
const telaBatalha = document.getElementById('tela-batalha');
const playerBattle = document.getElementById('player-battle');
const enemyBattle = document.getElementById('enemy-battle');
const battleArea = document.getElementById('battle-area');

const playerHpBar = document.getElementById('player-hp');
const enemyHpBar = document.getElementById('enemy-hp');

// ========================
// FUNÇÃO PISCAR DANO
// ========================
function piscarDano(element, callback){
  let count=0;
  const interval=setInterval(()=>{
    element.style.opacity=(count%2===0)?0:1;
    count++;
    if (count>5) {
      clearInterval(interval);
      element.style.opacity=1;
      if (callback) callback();
    }
  },100);
}

// ========================
// ATUALIZA BARRAS DE HP
// ========================
async function atualizarHP() {
  const jogador = await pegarJogadorPeloId(idJogador);
  const inimigo = await pegarInimigoPeloId(idInimigo);
  
  playerHpBar.textContent = `${jogador.vida}/${jogador.vidaMaxima}`;
  enemyHpBar.textContent = `${inimigo.vida}/${inimigo.vidaMaxima}`;

  playerHpBar.style.width = `${(jogador.vida/jogador.vidaMaxima)*60}px`;
  enemyHpBar.style.width = `${(inimigo.vida/inimigo.vidaMaxima)*60}px`;

  // Atualiza posição das barras acima dos sprites
  playerHpBar.style.left = playerBattle.offsetLeft + "px";
  playerHpBar.style.top = (playerBattle.offsetTop - 15) + "px";

  enemyHpBar.style.left = enemyBattle.offsetLeft + "px";
  enemyHpBar.style.top = (enemyBattle.offsetTop - 15) + "px";
}

// ========================
// TURNOS
// ========================
let turno = "player"; // player ou enemy
let inimigoTimeout = null;

function setTurno(novoTurno){
  turno = novoTurno;

  const btns = [
    document.getElementById('btn-lutar'),
    document.getElementById('btn-magia'),
    document.getElementById('btn-correr')
  ];

  if(turno === "player"){
    btns.forEach(b => b.disabled = false);
  } else {
    btns.forEach(b => b.disabled = true);
    inimigoTimeout = setTimeout(() => {
      turnoInimigo();
    }, 2000); // turno do inimigo dura 2s
  }
}

// ========================
// TURNO INIMIGO
// ========================
async function turnoInimigo() {
  const originalX = enemyBattle.offsetLeft;
  enemyBattle.style.left = (originalX - 16) + "px";
  
  const ataque = 1;

  const statusBatalha = await batalha(idJogador, idInimigo, idInimigo, ataque)
  console.log(statusBatalha);

  const mensagemTelaExibir = document.getElementById("battle-log");
  mensagemTelaExibir.textContent = statusBatalha.mensagem;
  
  if (statusBatalha.batalhaFinalizada === true) {
    atualizarHP();
    setTimeout(() => {
        window.location.href='index.html'
    }, 4000)
  } else {
    setTimeout(() => {
    enemyBattle.style.left = originalX + "px";
    piscarDano(playerBattle, () => {
      atualizarHP();
      setTurno("player");
    });
  }, 200);
  }
}

// ========================
// INICIAR BATALHA
// ========================
function iniciarBatalha(){
  setTurno("player");
  atualizarHP();
}

// ========================
// BOTÕES DO PLAYER
// ========================
document.getElementById('btn-lutar').addEventListener('click', async ()=>{
  if(turno !== "player") return;
  const ataque = document.getElementById('btn-lutar').value;

  const statusBatalha = await batalha(idJogador, idInimigo, idJogador, ataque)
  const mensagemTelaExibir = document.getElementById("battle-log");
  mensagemTelaExibir.textContent = statusBatalha.mensagem;
  
  if (statusBatalha.batalhaFinalizada === true) {
    atualizarHP();
    setTimeout(() => {
        window.location.href='index.html'
    }, 4000)
  } else {
    setTimeout(()=>{
    playerBattle.style.left = originalX + 'px';
    piscarDano(enemyBattle, atualizarHP);
  }, 200);
  }

  const originalX = playerBattle.offsetLeft;
  playerBattle.style.left = originalX + 16 + 'px';
  /*
  setTimeout(()=>{
    playerBattle.style.left = originalX + 'px';
    piscarDano(enemyBattle, atualizarHP);
  },200);
  */
  if (statusBatalha.batalhaFinalizada === false) {
    setTurno("enemy");
  } 
});

document.getElementById('btn-magia').addEventListener('click', async ()=>{
  if(turno !== "player") return;
  const magia = document.getElementById('btn-magia').value;

  const statusBatalha = await batalha(idJogador, idInimigo, idJogador, magia)
  const mensagemTelaExibir = document.getElementById("battle-log");
  mensagemTelaExibir.textContent = statusBatalha.mensagem;

  const proj = document.createElement('div');
  proj.style.position='absolute';
  proj.style.width='40px';
  proj.style.height='40px';
  proj.style.backgroundColor='orange';
  proj.style.left=playerBattle.offsetLeft+'px';
  proj.style.top=playerBattle.offsetTop+'px';
  proj.style.imageRendering='pixelated';
  battleArea.appendChild(proj);

  const targetX=enemyBattle.offsetLeft;
  const targetY=enemyBattle.offsetTop;

  const passoX=(targetX-playerBattle.offsetLeft)/20;
  const passoY=(targetY-playerBattle.offsetTop)/20;
  let count=0;

  const anim=setInterval(()=>{
    proj.style.left=parseFloat(proj.style.left)+passoX+'px';
    proj.style.top=parseFloat(proj.style.top)+passoY+'px';
    count++;
    if(count>20){
      clearInterval(anim);
      battleArea.removeChild(proj);
      piscarDano(enemyBattle, atualizarHP);
    }
  }, 20);

  if (statusBatalha.batalhaFinalizada === true) {
    atualizarHP();
    setTimeout(() => {
        window.location.href='index.html'
    }, 4000)
  } else {
    setTurno("enemy");
  }
});

document.getElementById('btn-correr').addEventListener('click', ()=>{
  if(turno !== "player") return;

  mostrarTela('tela-jogo');
  posX=inimigoX-64;
  posY=inimigoY;
  atualizarPosicoes();
  emBatalha=false;

  clearTimeout(inimigoTimeout);
});

// ========================
// REDIMENSIONAMENTO DINÂMICO DA TELA-JOGO
// ========================
function ajustarTelaJogo() {
  const centerX = window.innerWidth / 2;
  const centerY = window.innerHeight / 2;

  // Ajusta personagem e inimigo proporcionalmente
  posX = centerX - 10 * bloco;
  posY = centerY;
  personagem.style.left = posX + 'px';
  personagem.style.top = posY + 'px';

  inimigoX = centerX + 10 * bloco;
  inimigoY = centerY;
  inimigo.style.left = inimigoX + 'px';
  inimigo.style.top = inimigoY + 'px';

  atualizarPosicoes();
}

// Evento para redimensionamento
window.addEventListener('resize', ajustarTelaJogo);

// Chama ao iniciar para ajustar à tela atual
ajustarTelaJogo();