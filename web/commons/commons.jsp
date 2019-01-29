<script type="application/javascript">
    $(function () {
        $("a").click(function () {
            var serializeVal = $(":hidden").serialize();

            window.location.href = this.href + "&" + serializeVal;
            return false;
        });
    })
</script>

<input type="hidden" name="minPrice" value="${param.minPrice}">
<input type="hidden" name="maxPrice" value="${param.maxPrice}">