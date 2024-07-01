package com.cardproject.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardproject.myapp.dao.PaymentDAO;
import com.cardproject.myapp.dto.PaymentDTO;
import com.cardproject.myapp.dto.TradeDTO;


@Service
public class PaymentService {

    @Autowired
    private PaymentDAO paymentDAO;

    // 거래 조회
 	public TradeDTO selectTradeById(int tradeId) {
         return paymentDAO.selectTradeById(tradeId);
    }
 	
 	// 결제 내용 추가
 	public int insertPayment(PaymentDTO payment) {
 		return paymentDAO.insertPayment(payment);
 	}
 	
 	// 포인트 사용
 	public int usingPoint(int amount, String userid, String cmt) {
		return paymentDAO.usingPoint(amount, userid, cmt);
	}
 	
 	// 결제 여부 갱신
 	public int updatePaid(int tradeId) {
		return paymentDAO.updatePaid(tradeId);
	}
}
