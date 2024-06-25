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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardproject.myapp.dao.DeckMakerDAO;
import com.cardproject.myapp.dto.DeckDTO;
import com.cardproject.myapp.dto.DeckSourceDTO;
import com.cardproject.myapp.dto.DigimonDTO;
import com.cardproject.myapp.dto.OnepieceDTO;
import com.cardproject.myapp.dto.PokemonDTO;
import com.cardproject.myapp.dto.YugiohDTO;
import com.cardproject.myapp.service.DeckMakerService;



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
	
	//포켓몬카드
	@RequestMapping("/pokemonDeckMaker.do")
    public void pokemonDeckMaker(Model model, @RequestParam(defaultValue = "1") int page) {
		String userid = null;
		 
		 if(session.getAttribute("userid") != null) {
			 userid = (String) session.getAttribute("userid");
			 System.out.println(userid);
		 }else {
			 System.out.println("null");
		 }
		
		List<PokemonDTO> pCardList = deckMakerService.selectAllPCard(page);
        System.out.println(pCardList.size());
        model.addAttribute("pCardList", pCardList);
        model.addAttribute("currentPage", page);
    }
	
	 //유희왕카드
	@RequestMapping("/yugiohDeckMaker.do")
    public void yugiohDeckMaker(Model model, @RequestParam(defaultValue = "1") int page) {
        List<YugiohDTO> yCardList = deckMakerService.selectAllYCard(page);
        System.out.println(yCardList.size());
        model.addAttribute("yCardList", yCardList);
    }
	
	//디지몬카드
	@RequestMapping("/digimonDeckMaker.do")
    public void digimonDeckMaker(Model model, @RequestParam(defaultValue = "1") int page) {
        List<DigimonDTO> dCardList = deckMakerService.selectAllDCard(page);
        System.out.println(dCardList.size());
        model.addAttribute("dCardList", dCardList);
    }
	
	//원피스카드
	@RequestMapping("/onepieceDeckMaker.do")
    public void onepieceDeckMaker(Model model, @RequestParam(defaultValue = "1") int page) {
        List<OnepieceDTO> oCardList = deckMakerService.selectAllOCard(page);
        System.out.println(oCardList.size());
        model.addAttribute("oCardList", oCardList);
    }
	

	 @GetMapping("/loadMorePCard.do")
	    public  @ResponseBody List<PokemonDTO> loadMorePCard(@RequestParam int page) {
		 List<PokemonDTO> plist = deckMakerService.selectAllPCard(page);
		 System.out.println("plist:" + plist.size());
	        return plist;
	    }
	 @GetMapping("/loadMoreYCard.do")
	    public  @ResponseBody List<YugiohDTO> loadMoreYCard(@RequestParam int page) {
		 List<YugiohDTO> ylist = deckMakerService.selectAllYCard(page);
		 System.out.println("ylist:" + ylist.size());
	        return ylist;
	    }
	 @GetMapping("/loadMoreDCard.do")
	    public  @ResponseBody List<DigimonDTO> loadMoreDCard(@RequestParam int page) {
		 List<DigimonDTO> dlist = deckMakerService.selectAllDCard(page);
		 System.out.println("dlist:" + dlist.size());
	        return dlist;
	    }
	 @GetMapping("/loadMoreOCard.do")
	    public  @ResponseBody List<OnepieceDTO> loadMoreOCard(@RequestParam int page) {
		 List<OnepieceDTO> olist = deckMakerService.selectAllOCard(page);
		 System.out.println("olist:" + olist.size());
	        return olist;
	    }
	 @PostMapping("/insertDeck.do")
	    public String addDeck(@RequestParam String kind, 
				    		  @RequestParam String deckTitle,
				    		  @RequestParam String commentBox, 
				    		  @RequestParam("imgList[]") String[] imgList,
	                          HttpSession session, Model model) {

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

	        
	            deckMakerService.saveDeckSource(deckDTO,imgList,kind );
	         

	        return "redirect:/deckMakers/deckListMain.do";
	    }

	 @GetMapping("/conditionSearch.do")
	    @ResponseBody
	    public List<PokemonDTO> conditionSearch(@RequestParam(value = "card_type", required = false) String cardType,
	                                            @RequestParam(value = "card_sort", required = false) String cardSort) {
	        Map<String, String> params = new HashMap<>();
	        params.put("card_type", cardType);
	        params.put("card_sort", cardSort);
	        System.out.println("cardtype,cardsort:" + params);
	     
	        return deckMakerService.filterCard(params);
	    }
}
