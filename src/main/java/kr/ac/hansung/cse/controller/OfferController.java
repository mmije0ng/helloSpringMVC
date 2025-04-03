package kr.ac.hansung.cse.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.model.Offer;
import kr.ac.hansung.cse.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// Controller -> Service -> Dao
@Controller
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping("/offers")
    public String showOffers(Model model) {
        List<Offer> offers = offerService.getAllOffers();
        model.addAttribute("id_offers", offers);

        return "offers";
    }

    @GetMapping("/createoffer")
    public String createOffer(Model model) {
        model.addAttribute("offer", new Offer());

        return "createoffer";
    }

    // @Valid Offer offer는 입력값과 유효성 검사 대상
    // Model model은 그 결과(offer 객체, 에러 메시지 등)를 뷰에 넘기기 위한 통로
    @PostMapping("/docreate")
    public String doCreate(Model model, @Valid Offer offer, BindingResult result) {
        System.out.println(offer);

        // 유효성 검사 결과가 BindingResult에 저장됨
        if(result.hasErrors()) {
            System.out.println("== Form data does not validated ==");

            List<ObjectError> errors = result.getAllErrors();

            for(ObjectError error:errors) {
                System.out.println(error.getDefaultMessage());
            }

            return "createoffer";
        }

        // Controller -> Service -> Dao
        offerService.insertOffer(offer);
        return "offercreated";
    }
}
