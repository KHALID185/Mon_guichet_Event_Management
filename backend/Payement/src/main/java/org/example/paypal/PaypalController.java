package org.example.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.DTO.PaymentRequestDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment")
public class PaypalController {
    private final PaypalService paypalService;

    @GetMapping("/")
    public String home() {
        return "index"; //index=web page
    }

    @PostMapping("/create")
    public String createPayment(
            @RequestBody PaymentRequestDto paymentRequestDto
    ){
        try {
            // Ensure the success and cancel URLs are pointing to your frontend
            String cancelUrl = "http://localhost:8099/payment-cancel";
            String successUrl = "http://localhost:8099/payment/success";

            Payment payment = paypalService.createPayment(
                    Double.valueOf(paymentRequestDto.getAmount()),
                    paymentRequestDto.getCurrency(),
                    paymentRequestDto.getMethod(),
                    "sale",
                    paymentRequestDto.getDescription(),
                    cancelUrl,
                    successUrl
            );

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return links.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            log.error("Error occurred::", e);
        }
        return "/error";
    }

    @GetMapping("/success")
    public RedirectView paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return new RedirectView("http://localhost:4200/payment-success?paymentId=" + paymentId + "&PayerID=" + payerId);
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
        }
        // If payment not approved or error occurred, redirect to error page
        return new RedirectView("http://localhost:4200/payment-error");
    }


    @GetMapping("/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/error")
    public String paymentError() {
        return "paymentError";
    }
}
