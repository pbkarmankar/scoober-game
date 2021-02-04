package com.takeaway.scoobergame.interfaces.webui;

import com.takeaway.scoobergame.application.ScooberGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/playGame")
public class GameWebUIController {

    @Autowired
    private ScooberGameService scooberGameService;

    @GetMapping()
    public String index() {
        return "index";
    }

}
