package com.artesansoul.artesansoulV3.Controllers;


import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Repositorio.ClienteRepository;
import com.artesansoul.artesansoulV3.services.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/clientes")
public class ClienteController {




    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AuthenticationManager authenticationManager;



    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }


    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String cadastrarCliente(
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("senha") String senha,
            Model model) {

        if (clienteService.existsByEmail(email)) {
            model.addAttribute("erro", "E-mail já cadastrado.");
            return "cadastro";
        }

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);
        cliente.setSenha(passwordEncoder.encode(senha));

        clienteService.salvar(cliente);

        model.addAttribute("sucesso", "Cadastro realizado com sucesso! Faça login.");
        return "redirect:/clientes/login";
    }


    @PostMapping("/login")
    public String processarLogin(@RequestParam String email,
                                 @RequestParam String senha,
                                 HttpSession session,
                                 Model model) {

        try {
            // Autentica o usuário
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, senha);

            Authentication auth = authenticationManager.authenticate(authToken);

            // Obtém o Cliente autenticado
            Cliente cliente = clienteService.buscarPorEmail(email);

            if (cliente == null) {
                model.addAttribute("erro", "E-mail ou senha inválidos.");
                return "login";
            }

            // Armazena na sessão
            session.setAttribute("clienteLogado", cliente);

            // Redireciona para página inicial ou dashboard
            return "redirect:/";

        } catch (AuthenticationException e) {
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            return "login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        // Redireciona para a página inicial ou login após logout
        return "redirect:/";
    }



    @PostMapping("/atualizar")
    public String atualizarPerfil(@RequestParam("nome") String nome,
                                  @RequestParam("email") String email,
                                  @RequestParam("telefone") String telefone,
                                  @RequestParam(value = "senha", required = false) String senha,
                                  @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
                                  HttpSession session,
                                  Model model) {

        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");

        if (cliente != null) {
            cliente.setNome(nome);
            cliente.setEmail(email);
            cliente.setTelefone(telefone);

            if (senha != null && !senha.isEmpty()) {
                cliente.setSenha(senha); // Pode criptografar se quiser
            }

            if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                try {
                    String diretorioUsuario = "uploads/" + cliente.getEmail() + "/";
                    Files.createDirectories(Paths.get(diretorioUsuario));

                    // Deleta a foto antiga
                    if (cliente.getFotoPerfil() != null && !cliente.getFotoPerfil().isEmpty()) {
                        Path caminhoAntigo = Paths.get(cliente.getFotoPerfil().replaceFirst("/", "")); // remove barra inicial
                        if (Files.exists(caminhoAntigo)) {
                            Files.delete(caminhoAntigo);
                        }
                    }

                    String nomeArquivo = "fotoPerfil_" + System.currentTimeMillis() + "_" + fotoPerfil.getOriginalFilename();
                    Path caminhoNovo = Paths.get(diretorioUsuario + nomeArquivo);

                    Files.copy(fotoPerfil.getInputStream(), caminhoNovo);

                    cliente.setFotoPerfil("/" + diretorioUsuario + nomeArquivo);
                } catch (Exception e) {
                    e.printStackTrace();
                    model.addAttribute("erro", "Erro ao fazer upload da foto de perfil.");
                    return "redirect:/gerenciarConta/TelaGerenciar";
                }
            }

            clienteRepository.save(cliente);
            session.setAttribute("clienteLogado", cliente);
        }

        return "redirect:/gerenciarConta/TelaGerenciar";
    }




}
