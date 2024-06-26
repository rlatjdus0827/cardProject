package com.cardproject.myapp.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardproject.myapp.dao.DeckMakerDAO;
import com.cardproject.myapp.dto.DeckDTO;
import com.cardproject.myapp.dto.DeckSourceDTO;
import com.cardproject.myapp.dto.DigimonDTO;
import com.cardproject.myapp.dto.NotificationDTO;
import com.cardproject.myapp.dto.OnepieceDTO;
import com.cardproject.myapp.dto.PokemonDTO;
import com.cardproject.myapp.dto.UserDTO;
import com.cardproject.myapp.dto.YugiohDTO;
import com.cardproject.myapp.service.DeckMakerService;
import com.cardproject.myapp.service.MyPageService;

import kotlin.internal.RequireKotlin;

@Controller
@RequestMapping("/deckMakers")
public class DeckMakerController {

	@GetMapping("/deckListDetail.do")
	public void auctionMain() {
		System.out.println("deckDetail page");

	}

	@GetMapping("/deckListMain.do")
	public void auctionDetail() {
		System.out.println("deckListMain page");

	}
	

	@Autowired
	private DeckMakerService deckMakerService;
	@Autowired
	HttpSession session;
	@Autowired
	MyPageService mpService;


	// 포켓몬카드 -------------------------------------------------------------------
	@RequestMapping("/pokemonDeckMaker.do")
	public void pokemonDeckMaker(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "card_type", required = false, defaultValue = "t") String cardType,
			@RequestParam(value = "card_sort", required = false, defaultValue = "s") String cardSort) {
		Map<String, String> params = new HashMap<>();
		
		String userid = (String) session.getAttribute("userid");
		params.put("card_type", cardType);
		params.put("card_sort", cardSort);
		List<PokemonDTO> pCardList = deckMakerService.getPCardList(page, params);
		System.out.println(pCardList.size());
		model.addAttribute("pCardList", pCardList);
		model.addAttribute("currentPage", page);
		if (userid != null) {
	
			UserDTO user = mpService.selectUserById(userid);
			List<NotificationDTO> nlist = mpService.selectFiveNotifications(userid);
			model.addAttribute("user", user);
			model.addAttribute("nlist", nlist);
		}

	}

	@GetMapping(value="/loadMorePCard.do", produces="application/json")
	public @ResponseBody List<PokemonDTO> loadMorePCard(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "card_type", required = false, defaultValue = "t") String cardType,
			@RequestParam(value = "card_sort", required = false, defaultValue = "s") String cardSort,
			@RequestParam String cardName) {//추가한부분
		Map<String, String> params = new HashMap<>();

		params.put("card_type", cardType);
		params.put("card_sort", cardSort);
		params.put("card_name", cardName); //추가한부분
		List<PokemonDTO> pCardList = deckMakerService.getPCardList(page, params);
		System.out.println(pCardList.size());
		return pCardList;
	}

	// 유희왕카드 -------------------------------------------------------------------
	@RequestMapping("/yugiohDeckMaker.do")
	public void yugiohDeckMaker(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "card_attr", required = false, defaultValue = "a") String cardAttr,
			@RequestParam(value = "card_sort", required = false, defaultValue = "s") String cardSort) {
		Map<String, String> params = new HashMap<>();

		String userid = (String) session.getAttribute("userid");
		params.put("card_attr", cardAttr);
		params.put("card_sort", cardSort);
		List<YugiohDTO> yCardList = deckMakerService.getYCardList(page, params);
		System.out.println(yCardList.size());
		model.addAttribute("yCardList", yCardList);
		model.addAttribute("currentPage", page);
		if (userid != null) {
			
			UserDTO user = mpService.selectUserById(userid);
			List<NotificationDTO> nlist = mpService.selectFiveNotifications(userid);
			model.addAttribute("user", user);
			model.addAttribute("nlist", nlist);
		}
	}

	@GetMapping(value="/loadMoreYCard.do", produces="application/json")
	public @ResponseBody List<YugiohDTO> loadMoreYCard(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "card_attr", required = false, defaultValue = "a") String cardAttr,
			@RequestParam(value = "card_sort", required = false, defaultValue = "s") String cardSort) {
		Map<String, String> params = new HashMap<>();

		params.put("card_attr", cardAttr);
		params.put("card_sort", cardSort);
		List<YugiohDTO> yCardList = deckMakerService.getYCardList(page, params);
		System.out.println(yCardList.size());
		return yCardList;
	}

	// 디지몬카드 -------------------------------------------------------------------
	@RequestMapping("/digimonDeckMaker.do")
	public void digimonDeckMaker(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "card_attr", required = false, defaultValue = "a") String cardAttr,
			@RequestParam(value = "card_sort", required = false, defaultValue = "s") String cardSort) {
		Map<String, String> params = new HashMap<>();

		String userid = (String) session.getAttribute("userid");
		params.put("card_attr", cardAttr);
		params.put("card_sort", cardSort);
		List<DigimonDTO> dCardList = deckMakerService.getDCardList(page, params);
		System.out.println(dCardList.size());
		model.addAttribute("dCardList", dCardList);
		model.addAttribute("currentPage", page);
		if (userid != null) {
			
			UserDTO user = mpService.selectUserById(userid);
			List<NotificationDTO> nlist = mpService.selectFiveNotifications(userid);
			model.addAttribute("user", user);
			model.addAttribute("nlist", nlist);
		}
	}

	@GetMapping(value="/loadMoreDCard.do", produces="application/json")
	public @ResponseBody List<DigimonDTO> loadMoreDCard(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "card_attr", required = false, defaultValue = "a") String cardAttr,
			@RequestParam(value = "card_sort", required = false, defaultValue = "s") String cardSort) {
		Map<String, String> params = new HashMap<>();

		params.put("card_attr", cardAttr);
		params.put("card_sort", cardSort);
		List<DigimonDTO> dCardList = deckMakerService.getDCardList(page, params);
		System.out.println(dCardList.size());
		return dCardList;
	}

	// 원피스카드 -------------------------------------------------------------------
	@RequestMapping("/onepieceDeckMaker.do")
	public void onepieceDeckMaker(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "card_attr", required = false, defaultValue = "a") String cardAttr,
			@RequestParam(value = "card_sort", required = false, defaultValue = "s") String cardSort) {
		Map<String, String> params = new HashMap<>();

		String userid = (String) session.getAttribute("userid");
		params.put("card_attr", cardAttr);
		params.put("card_sort", cardSort);
		List<OnepieceDTO> oCardList = deckMakerService.getOCardList(page, params);
		System.out.println(oCardList.size());
		model.addAttribute("oCardList", oCardList);
		model.addAttribute("currentPage", page);
		if (userid != null) {
			
			UserDTO user = mpService.selectUserById(userid);
			List<NotificationDTO> nlist = mpService.selectFiveNotifications(userid);
			model.addAttribute("user", user);
			model.addAttribute("nlist", nlist);
		}
	}

	@GetMapping(value="/loadMoreOCard.do", produces="application/json")
	public @ResponseBody List<OnepieceDTO> loadMoreOCard(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(value = "card_attr", required = false, defaultValue = "a") String cardAttr,
			@RequestParam(value = "card_sort", required = false, defaultValue = "s") String cardSort) {
		Map<String, String> params = new HashMap<>();

		params.put("card_attr", cardAttr);
		params.put("card_sort", cardSort);
		List<OnepieceDTO> oCardList = deckMakerService.getOCardList(page, params);
		System.out.println(oCardList.size());
		return oCardList;
	}

	//insert
	@PostMapping("/insertDeck.do")
	public String addDeck(@RequestParam String kind, @RequestParam String deckTitle, @RequestParam String commentBox,
			@RequestParam("imgList[]") String[] imgList, HttpSession session, Model model) {

		String userId = (String) session.getAttribute("userid");

		if (userId == null) {
			throw new RuntimeException("로그인 후 이용해주십시오.");
		}

		System.out.println(userId);
		DeckDTO deckDTO = new DeckDTO();
		deckDTO.setDeck_title(deckTitle);
		deckDTO.setCmt(commentBox);
		deckDTO.setUser_id(userId);
		deckDTO.setRecommend(0);

		switch (kind) {
		case "P":
			deckDTO.setCat(1);
			break;
		case "Y":
			deckDTO.setCat(2);
			break;
		case "D":
			deckDTO.setCat(3);
			break;
		case "O":
			deckDTO.setCat(4);
			break;
		}

		deckMakerService.saveDeckSource(deckDTO, imgList, kind);

		return "redirect:deckListMain.do";
	}

}
