package com.github.ontio.txPush.model;

import com.github.ontio.model.dao.TxDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhouq
 * @version 1.0
 * @date 2020/3/27
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PushEmailDto {

    private String email;
    private String userName;
    private String ontId;
    private String userAddress;
    private String txDes;
    private String txHash;
    private String assetName;
    private String amount;
    private String time;
    private String toAddress;
    private String fromAddress;

    public static final String DEPOSIT = "deposit";
    public static final String WITHDRAW = "withdraw";


    public static PushEmailDto buildDto(PushUserAddressInfoDto pushUserAddressInfoDto, TxDetail txDetail, String txDes) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PushEmailDtoBuilder builder = PushEmailDto.builder()
                .email(pushUserAddressInfoDto.getEmail())
                .userName(pushUserAddressInfoDto.getUserName())
                .ontId(pushUserAddressInfoDto.getOntId())
                .txHash(txDetail.getTxHash())
                .amount(txDetail.getAmount().stripTrailingZeros().toPlainString())
                .time(sdf.format(new Date(txDetail.getTxTime() * 1000L)))
                .assetName(txDetail.getAssetName())
                .fromAddress(txDetail.getFromAddress())
                .toAddress(txDetail.getToAddress());
        if (DEPOSIT.equals(txDes)) {
            return builder.userAddress(txDetail.getToAddress())
                    .txDes(DEPOSIT)
                    .build();
        } else {
            return builder.userAddress(txDetail.getFromAddress())
                    .txDes(WITHDRAW)
                    .build();
        }
    }


}