package cloud9.webapplication.purchase.voucherseries.controller;

import cloud9.webapplication.purchase.voucherseries.service.VoucherSeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/series/")
@RequiredArgsConstructor
public class SeriesController {

    private final VoucherSeriesService sequenceService;

    @GetMapping("Last/{prefix}")
    public ResponseEntity<String> getVoucherNumberSequence(@PathVariable String prefix) {
        String preview = sequenceService.getLastNumber(prefix.toUpperCase());
        return ResponseEntity.ok(preview);
    }
}
