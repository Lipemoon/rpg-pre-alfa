async function deletarJogador(idJogador) {
  const url = `${window.location.origin}/rpg-game/jogador/${idJogador}`
  const res = await fetch(url, {
        mode: 'cors',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: "DELETE",
    })
    if (res.status != 204) {
      const exception = await res.json();
      throw new Error(exception.message);
    }
}

async function deletarInimigo(idInimigo) {
  const url = `${window.location.origin}/rpg-game/inimigo/${idInimigo}`
  const res = await fetch(url, {
        mode: 'cors',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: "DELETE",
    })
    if (res.status != 204) {
      const exception = await res.json();
      throw new Error(exception.message);
    }
}

async function criarJogador(forca, vida, magia) {
  const url = `${window.location.origin}/rpg-game/jogador`
  const res = await fetch(url, {
        mode: 'cors',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify({
            forca: forca,
            vida: vida,
            magia: magia
        })
    })
    if (res.status != 201) {
      const exception = await res.json();
      throw new Error(exception.message);
    } else {
      const resposta = await res.json();
      console.log(resposta.id)
      return resposta.id;
    }
}
async function criarInimigo() {
  const url = `${window.location.origin}/rpg-game/inimigo`
  const res = await fetch(url, {
        mode: 'cors',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: "POST",
    })
    if (res.status != 201) {
      const exception = await res.json();
      throw new Error(exception.message);
    } else {
      const resposta = await res.json();
      return resposta.id;
    }
}

async function pegarJogadorPeloId(idJogador) {
  const url = `${window.location.origin}/rpg-game/jogador/${idJogador}`
  const res = await fetch(url, {
        mode: 'cors',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: "GET",
    })
    if (res.status != 200) {
      const exception = await res.json();
      throw new Error(exception.message);
    } else {
      const resposta = await res.json();
      return resposta;
    }
}

async function pegarInimigoPeloId(idInimigo) {
  const url = `${window.location.origin}/rpg-game/inimigo/${idInimigo}`
  const res = await fetch(url, {
        mode: 'cors',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: "GET",
    })
    if (res.status != 200) {
      const exception = await res.json();
      throw new Error(exception.message);
    } else {
      const resposta = await res.json();
      return resposta;
    }
}

async function batalha(idJogador, idInimigo, idTurno, ataque) {
  const url = `${window.location.origin}/rpg-game/batalha/escolherAtaque`
  const res = await fetch(url, {
        mode: 'cors',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify({
            idJogador: idJogador,
            idInimigo: idInimigo,
            idTurno: idTurno,
            ataque: ataque
        })
    })
    if (res.status != 200) {
      const exception = await res.json();
      throw new Error(exception.message);
    } else {
      const resposta = await res.json();
      return resposta;
    }
}