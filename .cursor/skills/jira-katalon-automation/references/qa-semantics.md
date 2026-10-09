# QA semantics — widget locators

Source: `QA-Semantics-Guide.md` (product QA contract). This project's Katalon objects and page methods follow this file. Do not invent an id that Spy has not shown.

## Rule

In Katalon, spy the control and copy **Accessibility** (`content-desc` on Android, accessibility id on iOS). Use that value as the locator.

Do **not** use visible text as the primary locator — it changes with language (Français / English).

An id looks like `login_screen`, `login_screen_text_field`, or `login_screen_button_0`. The index counts only controls of the **same kind** on that screen, top to bottom, starting at `0`. If there is only one of that kind, there is no index.

Widget kinds: `button`, `text_field`, `check_box`, `toggle`, `menu_item`, `banner`.

Sheets and popups are their own screen id. Wait for that id. Do not tap the page behind them.

If Spy shows no id in this form, record `SEMANTIC_ID_MISSING` in the automation report and keep the existing fallback already on the object (or a class/index fallback that was seen on the device). Do not invent a name.

XPath form for a semantic id:

```text
//*[@content-desc='<id>' or @resource-id='<id>']
```

## Selector order (new objects)

1. Semantic accessibility / resource-id in the form above (this file).
2. Existing object already in `Object Repository/` that already uses that id.
3. Device-UI quirk fallback documented in [katalon-architecture.md](katalon-architecture.md) (PIN first-circle tap, MSISDN Next only while focused).
4. Relative XPath from a seen attribute. Absolute XPath last. No coordinates for normal controls.

## Known screens

Wait for the screen id before the first tap on that screen.

| Screen | Wait for | Notes |
| --- | --- | --- |
| Splash | `splash_screen` | Cold start |
| Language sheet | `language_bottom_sheet` | Overlay on onboarding. Close `_button_0`, first language `_button_1`, second language `_button_2` |
| Onboarding / MSISDN | `onboarding_screen` | Language `_button_0`, phone `onboarding_screen_text_field`, continue `_button_1` |
| Login / PIN | `login_screen` | One PIN field: `login_screen_text_field`. Buttons top to bottom: back `_0`, logo `_1`, biometric `_2`, login `_3`. Only toggle: `login_screen_toggle` |
| OTP | `otp_screen` | Back `_0`, logo `_1`, retry `_2`. Field: `otp_screen_text_field` |
| Permissions (new device after OTP) | `user_permissions_screen` | Back `_0`, logo `_1`, confirm `_2`. Terms: `user_permissions_screen_check_box` |
| Biometric enable (first login) | `biometric_enable_screen` | Enable: `_button_2` (back `_0`, logo `_1`) |
| Biometric sheet | `biometric_enable_bottom_sheet` | Close `_0`, OK `_1` |
| Alert / force update / logout confirm | `alert_popup` | Close `_0`, OK `_1`, cancel `_2` when shown |
| Blocking spinner | `network_loader` | Wait until gone |
| Home shell | `home_page` | Tabs left to right: Dashboard `_button_0`, Performance `_1`, Notifications `_2`, Profile `_3` |
| Dashboard | `dashboard_screen` | Lives inside `home_page` |
| Side menu | `side_menu` | See table below |

### Dashboard (`dashboard_screen`)

Header left to right: menu `_button_0`, search `_1`, activity history `_2`.

Balance card top to bottom: refresh `_button_3`, show/hide `_4`, QR `_5`.

Menu tiles are numbered across the whole grid, not per row: `dashboard_screen_menu_item_0`, `_1`, `_2`, … Nested flows opened from a tile are not in this map until Spy confirms them.

Banners: `dashboard_screen_banner_0`, `_1`, …

### Side menu (`side_menu`)

Back `_button_0`, Language `_button_1`. Then account actions top to bottom. If debug logs appear in the header they take `_button_2` and the list shifts by one.

| Action | Normal build | Debug logs visible |
| --- | --- | --- |
| My profile | `side_menu_button_2` | `side_menu_button_3` |
| Rate app | `side_menu_button_3` | `side_menu_button_4` |
| Share app | `side_menu_button_4` | `side_menu_button_5` |
| Logout | `side_menu_button_5` | `side_menu_button_6` |

Logout confirm: `alert_popup_button_1`. Biometric switch in the drawer: `side_menu_toggle`.

## Any new screen

Spy it, read the id, count from the top if there is more than one of that kind. Add the id to this file only after it was seen on the device or in an existing object. Do not guess the next index.

## Device-UI quirks that still apply

Semantic ids do not remove these behaviours. Keep them in page objects.

- PIN is four circles. Tap the first circle, then type. See [katalon-architecture.md](katalon-architecture.md).
- Onboarding continue (`onboarding_screen_button_1`) may exist only while `onboarding_screen_text_field` has focus. Do not hide the keyboard first.
- First-login biometric uses `biometric_enable_screen`. Treat that screen as login success. Tap Enable (`_button_2`). Do not restart the login journey.
- Linked devices may skip `otp_screen` and `user_permissions_screen`.
