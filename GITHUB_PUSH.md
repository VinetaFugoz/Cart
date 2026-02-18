# Subir o projeto só para o seu GitHub (sem Azure)

O projeto **ainda não é um repositório Git** (não existe pasta `.git` aqui).  
Assim você garante que **nenhum remote da empresa (Azure) é usado**.

## Passos

### 1. Criar o repositório no GitHub
- Acesse [github.com](https://github.com) e faça login na **sua conta pessoal**.
- **New repository** → nome, por ex.: `Cart` ou `android-cart-mvi`.
- **Não** inicialize com README (o projeto já existe).
- Anote a URL: `https://github.com/SEU_USUARIO/Cart.git`.

### 2. Inicializar Git e usar só o GitHub
No terminal, na pasta do projeto (`Cart`):

```bash
cd c:\Users\victo\AndroidStudioProjects\AndroidDeveloper\Cart

# Inicializar repositório (só local)
git init

# Adicionar APENAS o seu GitHub como remote (nome: origin)
git remote add origin https://github.com/SEU_USUARIO/Cart.git

# Verificar: deve aparecer só "origin" apontando pro GitHub
git remote -v
```

**Importante:** como você fez `git init` aqui e só adicionou o GitHub, **não existe remote da Azure**. Só existe `origin` → seu GitHub.

### 3. Primeiro commit e push
```bash
git add .
git commit -m "Initial commit: Cart app with MVI"
git branch -M main
git push -u origin main
```

---

## Se no futuro você adicionar outro remote

- **Só GitHub:** `git push origin main` (ou `git push`).
- Se um dia adicionar Azure em outro remote (ex.: `git remote add azure ...`), o push **só vai para Azure** se você rodar `git push azure main`. O `git push` sem nome continua indo para `origin` (GitHub), desde que você tenha configurado `origin` como no passo 2.

---

## Resumo

| O que você fez | Resultado |
|----------------|-----------|
| `git init` na pasta Cart | Repo Git só local, sem remotes da empresa |
| `git remote add origin URL_DO_SEU_GITHUB` | Único remote = seu GitHub |
| `git push -u origin main` | Código sobe só para o GitHub |

Assim você não sobe nada para o Azure da empresa.
